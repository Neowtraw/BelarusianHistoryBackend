package com.codingub.di

import com.codingub.data.HistoryDatabase
import com.codingub.data.repositories.TicketDataRepository
import com.codingub.data.repositories.TicketDataRepositoryImpl
import com.codingub.data.repositories.UserDataRepository
import com.codingub.data.repositories.UserDataRepositoryImpl
import com.codingub.security.hashing.HashingService
import com.codingub.security.hashing.SHA256HashingService
import com.codingub.security.token.JwtTokenService
import com.codingub.security.token.TokenConfig
import com.codingub.security.token.TokenService
import org.koin.dsl.module

val koinModule = module {
    single { provideDatabase()}
    single { provideHashingService() }
    single { provideTokenService() }

    single { provideUserDataRepository(get() as HistoryDatabase) }
    single { provideTicketDataRepository(get() as HistoryDatabase) }

}

internal fun provideHashingService() : HashingService = SHA256HashingService()
internal fun provideTokenService() : TokenService = JwtTokenService()



/*
    Database
 */

internal fun provideDatabase() : HistoryDatabase = HistoryDatabase()

/*
    Repositories
 */

internal fun provideUserDataRepository(
    database: HistoryDatabase
) : UserDataRepository = UserDataRepositoryImpl(database)

internal fun provideTicketDataRepository(
    database: HistoryDatabase
) : TicketDataRepository = TicketDataRepositoryImpl(database)