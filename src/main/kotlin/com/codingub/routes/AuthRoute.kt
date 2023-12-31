package com.codingub.routes

import com.codingub.data.models.users.UserDto
import com.codingub.data.repositories.UserDataRepository
import com.codingub.data.requests.LoginRequest
import com.codingub.data.requests.RegisterRequest
import com.codingub.data.responses.AuthResponse
import com.codingub.security.hashing.HashingService
import com.codingub.security.hashing.SaltedHash
import com.codingub.security.token.TokenClaim
import com.codingub.security.token.TokenConfig
import com.codingub.security.token.TokenService
import com.codingub.utils.Constants.EndPoints.ROUTE_AUTHENTICATE
import com.codingub.utils.Constants.EndPoints.ROUTE_SECRET
import com.codingub.utils.Constants.EndPoints.ROUTE_SIGNIN
import com.codingub.utils.Constants.EndPoints.ROUTE_SIGNUP
import com.codingub.utils.Constants.USER_ID
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.apache.commons.codec.digest.DigestUtils
import org.koin.java.KoinJavaComponent.inject


private val userDataSource: UserDataRepository by inject(UserDataRepository::class.java)
private val tokenService: TokenService by inject(TokenService::class.java)
private val hashingService: HashingService by inject(HashingService::class.java)
fun Route.signUp() {
    post(ROUTE_SIGNUP) {
        val request = kotlin.runCatching { call.receiveNullable<RegisterRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val saltedHash = hashingService.generateSaltedHash(request.password)
        val user = UserDto(
            login = request.login,
            password = saltedHash.hash,
            username = request.username,
            accessLevel = request.accessLevel,
            salt = saltedHash.salt
        )
        val wasAcknowledged = userDataSource.insertUser(user)
        if (!wasAcknowledged) {
            call.respond(
                HttpStatusCode.Conflict,
                "A user with this login already exists"
            )
            return@post
        }
        call.respond(HttpStatusCode.OK)
    }
}

fun Route.signIn(
    tokenConfig: TokenConfig,
) {
    post(ROUTE_SIGNIN) {
        val request = kotlin.runCatching { call.receiveNullable<LoginRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val user = userDataSource.getUserByLogin(request.login)
        if (user == null) {
            call.respond(HttpStatusCode.Conflict, "Incorrect username or password")
            return@post
        }

        val isValidPassword = hashingService.verify(
            value = request.password,
            saltedHash = SaltedHash(
                hash = user.password,
                salt = user.salt
            )
        )
        if (!isValidPassword) {
            println("Entered hash: ${DigestUtils.sha256Hex("${user.salt}${request.password}")}, Hashed PW: ${user.password}")
            call.respond(HttpStatusCode.Conflict, "Incorrect username or password")
            return@post
        }

        val token = tokenService.generate(
            config = tokenConfig,
            TokenClaim(
                name = USER_ID,
                value = user.id
            )
        )

        call.respond(
            status = HttpStatusCode.OK,
            message = AuthResponse(
                token = token,
                username = user.username,
                accessLevel = user.accessLevel,
                login = user.login
            )
        )
    }
}

fun Route.authenticate() {
    authenticate {
        get(ROUTE_AUTHENTICATE) {
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Route.getSecretInfo() {
    authenticate {
        get(ROUTE_SECRET) {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim(USER_ID, String::class)
            call.respond(HttpStatusCode.OK, "Your userId is $userId")
        }
    }
}
