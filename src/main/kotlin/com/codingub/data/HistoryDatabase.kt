package com.codingub.data

import com.codingub.data.models.achieves.AchieveDto
import com.codingub.data.models.tickets.PracticeQuestionDto
import com.codingub.data.models.tickets.TicketDto
import com.codingub.data.models.tickets.TicketQuestionDto
import com.codingub.data.models.users.GroupDto
import com.codingub.data.models.users.UserDto
import com.codingub.data.requests.*
import com.codingub.data.responses.*
import com.codingub.data.models.events.EventDto
import com.codingub.data.models.map.MapDto
import com.codingub.data.models.map.MapLabelDto
import com.codingub.data.models.map.MapPeriodDto
import com.codingub.data.models.map.MapTypeDto
import com.codingub.data.models.users.ResultDto
import com.codingub.data.responses.models.PracticeQuestion
import com.codingub.data.responses.models.Ticket
import com.codingub.data.responses.models.TicketQuestion
import com.codingub.data.responses.models.map.Map
import com.codingub.data.responses.models.map.MapLabel
import com.codingub.data.responses.models.map.MapType
import com.codingub.sdk.AccessLevel
import com.codingub.sdk.AchieveType
import com.codingub.sdk.MapCategory
import com.codingub.utils.Constants
import com.codingub.utils.HistoryLogger
import com.mongodb.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.withContext
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.abortTransactionAndAwait
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.coroutine.insertOne
import org.litote.kmongo.reactivestreams.KMongo

class HistoryDatabase {

    private val client = KMongo.createClient(
        connectionString = "mongodb+srv://neowtraw:${Constants.MONGO_PW}@historycluster.xg5enip.mongodb.net/${Constants.MONGO_DB_NAME}?retryWrites=true&w=majority"
    )
    private val database = client.coroutine
        .getDatabase(Constants.MONGO_DB_NAME)

    //tables of all data
    private val userCollection = database.getCollection<UserDto>()
    private val ticketCollection = database.getCollection<TicketDto>()
    private val tqCollection = database.getCollection<TicketQuestionDto>()
    private val pqCollection = database.getCollection<PracticeQuestionDto>()
    private val achieveCollection = database.getCollection<AchieveDto>()
    private val groupCollection = database.getCollection<GroupDto>()
    private val eventCollection = database.getCollection<EventDto>()
    private val resultCollection = database.getCollection<ResultDto>()

    private val mapCollection = database.getCollection<MapDto>()
    private val mapTypeCollection = database.getCollection<MapTypeDto>()
    private val mapLabelCollection = database.getCollection<MapLabelDto>()
    private val mapPeriodCollection = database.getCollection<MapPeriodDto>()

    /*
        User
     */
    suspend fun getUserByLogin(login: String): UserDto? {
        return userCollection.findOne(UserDto::login eq login)
    }

    suspend fun insertUser(user: UserDto): Boolean {
        val foundUser = userCollection.findOne(UserDto::login eq user.login)

        if (foundUser != null) {
            HistoryLogger.info("Such login is already used")
            return false
        }

        HistoryLogger.info("New user created with login: ${user.login}")
        return userCollection.insertOne(user).wasAcknowledged()
    }

    suspend fun changeRoleByLogin(login: String, accessLevel: AccessLevel): Boolean {
        val user = userCollection.findOne(UserDto::login eq login)
        return if (user != null) {
            val updatedUser = user.copy(
                accessLevel = accessLevel
            )
            userCollection.updateOne(
                UserDto::login eq login,
                updatedUser
            ).wasAcknowledged()
        } else {
            false
        }
    }

    /*
        Group
     */

    suspend fun getAllGroups(login: String): GroupResponse {
        val user = userCollection.findOne(UserDto::login eq login)

        if (user?.accessLevel == AccessLevel.User) {
            val group = groupCollection.find().toList().firstOrNull { group ->
                group.users.contains(user.login)
            }
            return GroupResponse(group?.let { listOf(it) } ?: emptyList())
        }

        val groups = groupCollection.find(GroupDto::teacher eq login).toList()
        return GroupResponse(groups)
    }

    suspend fun createGroup(request: CreateGroupRequest): Boolean {
        val groups = groupCollection.find(GroupDto::teacher eq request.teacher).toList()
        groups.forEach {
            if (it.name == request.groupName) return false
        }
        return groupCollection.insertOne(GroupDto(name = request.groupName, teacher = request.teacher))
            .wasAcknowledged()
    }

    suspend fun deleteGroup(request: GroupRequest): Boolean {
        return if (groupCollection.findOne(GroupDto::id eq request.groupId) == null) false
        else groupCollection.deleteOne(GroupDto::id eq request.groupId).wasAcknowledged()
    }

    suspend fun inviteUserToGroup(request: GroupRequest): Boolean {
        val user = userCollection.findOne(UserDto::login eq request.login) ?: return false

        // проверка, состоит ли User в группах или является ли он учителем
        val isUserInGroups = groupCollection.find().toList().any { group ->
            group.users.contains(user.login)
        }

        if (groupCollection.findOne(GroupDto::teacher eq user.login) != null
            && isUserInGroups
        ) return false


        val group = groupCollection.findOne(GroupDto::id eq request.groupId) ?: return false
        val userList: MutableList<String> = group.users.toMutableList()
        userList.add(user.login)

        val updatedGroup = group.copy(
            users = userList
        )
        return groupCollection.updateOne(GroupDto::id eq request.groupId, updatedGroup).wasAcknowledged()
    }

    suspend fun deleteUserFromGroup(request: GroupRequest): Boolean {
        val user = userCollection.findOne(UserDto::login eq request.login) ?: return false

        val group = groupCollection.findOne(GroupDto::id eq request.groupId) ?: return false
        val userList: MutableList<String> = group.users.toMutableList()
        userList.remove(user.login)

        val updatedGroup = group.copy(
            users = userList
        )
        return groupCollection.updateOne(GroupDto::id eq request.groupId, updatedGroup).wasAcknowledged()
    }

    /*
        Ticket
     */

    suspend fun deleteTicketByName(name: String): Boolean {
//        val ticket = ticketCollection.findOne(TicketDto::name eq name)
//        val achieve = achieveCollection.findOne()
//        return if (ticket != null) {
//
//            ticketCollection.deleteOne(TicketDto::name eq name).wasAcknowledged()
//        } else {
//            HistoryLogger.info("Ticket not found")
        return false
        //    }
    }

    //                        val deletedResults = ids.map { id ->
//                        ticketCollection.findOneAndDelete(TicketDto::id eq id)?.let { deletedTicket ->
//                            tqCollection.find(TicketQuestionDto::ticketId eq id).toList().forEach { tq ->
//                                pqCollection.deleteMany(PracticeQuestionDto::tqId eq tq.id)
//                            }
//                            tqCollection.deleteMany(TicketQuestionDto::ticketId eq id)
//                            true
//                        } ?: false
    suspend fun deleteTicketsByIds(ids: List<String>): Boolean {
        val txnOptions: TransactionOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .writeConcern(WriteConcern.MAJORITY)
            .build()

        client.startSession().awaitFirst().use { session ->
            session.startTransaction(txnOptions)

            try {
                ids.forEach { id ->
                    with(ticketCollection) {
                        findOne(TicketDto::id eq id) ?: return false
                        deleteDataFromTicket(id)
                        deleteOne(TicketDto::id eq id).wasAcknowledged()
                    }
                }
                return true
            } catch (e: MongoCommandException) {
                session.abortTransactionAndAwait()
                return false
            } finally {
                session.close()
            }
        }
    }

    suspend fun getAllTickets(): TicketResponse {
        val tickets = ticketCollection.find().toList()
        val ticketMap = tickets.associate { ticket ->
            val achievement = achieveCollection.findOne(AchieveDto::ownerId eq ticket.id)

            val tqs = tqCollection.find(TicketQuestionDto::ticketId eq ticket.id).toList().map { tq ->
                val practices = pqCollection.find(PracticeQuestionDto::tqId eq tq.id).toList().map { practice ->
                    PracticeQuestion(
                        taskType = practice.taskType,
                        id = practice.id,
                        name = practice.name,
                        info = practice.info,
                        answers = practice.answers
                    )
                }
                val achieve = achieveCollection.findOne(AchieveDto::ownerId eq ticket.id)

                TicketQuestion(
                    id = tq.id,
                    name = tq.name,
                    info = tq.info,
                    practices = practices,
                    achieve = achieve
                )
            }
            ticket.id to Ticket(ticket.id, ticket.name, ticket.timer, tqs, achievement)
        }

        val ticketList = tickets.map { ticket ->
            val achievement = achieveCollection.findOne(AchieveDto::ownerId eq ticket.id)

            ticketMap[ticket.id] ?: Ticket(ticket.id, ticket.name, ticket.timer, emptyList(), achievement)
        }

        return TicketResponse(ticketList)
    }

    suspend fun insertOrUpdateTicket(request: InsertTicketRequest): Boolean {
        val ticket = ticketCollection.findOne(TicketDto::name eq request.name)

        if (request.achieve != null) {
            val achieve = achieveCollection.findOne(AchieveDto::ownerId eq ticket?.id)
            if (achieve != null)
                achieveCollection.replaceOne(AchieveDto::id eq request.achieve.id, request.achieve)
            else achieveCollection.insertOne(request.achieve)
        }

        if (ticket != null) {
            val updatedTicket = ticket.copy(
                name = request.name,
                timer = request.timer
            )
            if (ticket != updatedTicket)
                return ticketCollection.updateOne(
                    TicketDto::id eq ticket.id,
                    updatedTicket
                ).wasAcknowledged()
        } else {
            val insertedTicket = TicketDto(
                name = request.name,
                timer = request.timer
            )
            return ticketCollection.insertOne(insertedTicket).wasAcknowledged()
        }
        return false
    }

    /*
        TicketQuestion
     */
    suspend fun insertOrUpdateTq(question: InsertTqRequest): Boolean {
        val tq = tqCollection.findOne(TicketQuestionDto::name eq question.name)

        if (question.achieve != null) {
            val achieve = achieveCollection.findOne(AchieveDto::ownerId eq tq?.id)
            if (achieve != null)
                achieveCollection.replaceOne(AchieveDto::id eq question.achieve.id, question.achieve)
            else achieveCollection.insertOne(question.achieve)
        }

        if (tq != null) {
            val updatedTq =
                tq.copy(
                    name = question.name,
                    info = question.info
                )
            return tqCollection.replaceOne(
                TicketQuestionDto::id eq tq.id, updatedTq
            ).wasAcknowledged()
        }

        val insertedTq = TicketQuestionDto(
            name = question.name,
            info = question.info,
            ticketId = question.ticketId
        )

        return tqCollection.insertOne(insertedTq).wasAcknowledged()
    }

    suspend fun deleteTqById(questionId: String): Boolean {
        tqCollection.findOne(TicketQuestionDto::id eq questionId) ?: return false
        return tqCollection.deleteOne(TicketQuestionDto::id eq questionId).wasAcknowledged()
    }

    suspend fun deleteTqsByIds(ids: List<String>): Boolean {
        val txnOptions: TransactionOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .writeConcern(WriteConcern.MAJORITY)
            .build()

        client.startSession().awaitFirst().use { session ->
            session.startTransaction(txnOptions)
            try {
                ids.forEach { id ->
                    achieveCollection.findOneAndDelete(AchieveDto::ownerId eq id) ?: return false
                    tqCollection.findOne(TicketQuestionDto::id eq id) ?: return false
                    pqCollection.deleteMany(PracticeQuestionDto::tqId eq id)
                    if (!tqCollection.deleteOne(TicketQuestionDto::id eq id).wasAcknowledged()) return false
                }
                return true
            } catch (e: MongoCommandException) {
                session.abortTransactionAndAwait()
                return false
            } finally {
                session.close()
            }
        }
    }

    suspend fun getAllTqFromTicket(ticketId: String): TqResponse {
        val tqs = tqCollection.find(TicketQuestionDto::ticketId eq ticketId).toList()

        val ticketQuestionList = tqs.map { tq ->
            val achieve = achieveCollection.findOne(AchieveDto::ownerId eq tq.id)
            val practices = pqCollection.find(PracticeQuestionDto::tqId eq tq.id).toList()
                .map { practice ->
                    PracticeQuestion(
                        taskType = practice.taskType,
                        id = practice.id,
                        name = practice.name,
                        info = practice.info,
                        answers = practice.answers
                    )
                }
            TicketQuestion(tq.id, tq.name, tq.info, practices, achieve)
        }

        return TqResponse(ticketQuestionList)
    }

    suspend fun getAllTq(): TqResponse {
        val pqList = pqCollection.find().toList()
        val tqIdList = pqList.map { it.tqId }

        // тикеты с практикой
        val tqs = tqCollection.find(TicketQuestionDto::id `in` tqIdList).toList()

        val ticketQuestionList = tqs.map { tq ->
            val achieve = achieveCollection.findOne(AchieveDto::ownerId eq tq.id)
            val practices = pqList.filter { it.tqId == tq.id }.map { practice ->
                PracticeQuestion(
                    taskType = practice.taskType,
                    id = practice.id,
                    name = practice.name,
                    info = practice.info,
                    answers = practice.answers
                )
            }
            TicketQuestion(tq.id, tq.name, tq.info, practices, achieve)
        }
        return TqResponse(ticketQuestionList)
    }

    /*
       PracticeQuestion
    */

    suspend fun insertPractice(question: InsertPqRequest): Boolean {
        val pq = pqCollection.findOne(PracticeQuestionDto::name eq question.name)

        if (pq != null) {
            val updatedPq = pq.copy(
                info = question.info,
                taskType = question.taskType,
                answers = question.answers
            )

            return pqCollection.replaceOne(
                PracticeQuestionDto::id eq pq.id, updatedPq
            ).wasAcknowledged()
        }

        val insertedPq = PracticeQuestionDto(
            name = question.name,
            info = question.info,
            taskType = question.taskType,
            answers = question.answers,
            tqId = question.tqId
        )
        return pqCollection.insertOne(insertedPq).wasAcknowledged()

    }

    suspend fun deletePracticeById(questionId: String): Boolean {
        val pq = pqCollection.findOne(PracticeQuestionDto::id eq questionId)

        if (pq != null) {
            return pqCollection.deleteOne(PracticeQuestionDto::id eq questionId)
                .wasAcknowledged()
        }
        return false
    }

    suspend fun deletePracticesByIds(ids: List<String>): Boolean {
        ids.forEach { id ->

            with(pqCollection) {
                findOne(PracticeQuestionDto::id eq id)?.let {
                    if (!deleteOne(PracticeQuestionDto::id eq id).wasAcknowledged()) return false
                } ?: return false
            }
        }
        return true
    }

    suspend fun getAllPracticeFromTq(tqId: String): PqResponse {
        return PqResponse(pqCollection.find(PracticeQuestionDto::tqId eq tqId).toList().map { pq ->
            PracticeQuestion(
                id = pq.id,
                taskType = pq.taskType,
                name = pq.name,
                info = pq.info,
                answers = pq.answers
            )
        }
        )
    }

    /*
        Results
     */

    suspend fun setAchieveCompletedByUser(request: AddResultRequest): Boolean {
        userCollection.findOne(UserDto::login eq request.login) ?: return false
        return resultCollection.insertOne(
            ResultDto(
                type = request.achieve.type,
                answer = request.answer,
                achieveId = request.achieve.id,
                userLogin = request.login
            )
        ).wasAcknowledged()
    }

    suspend fun getResultsByType(login: String, type: AchieveType): ResultResponse {
        return ResultResponse(
            resultCollection.find(
                ResultDto::userLogin eq login,
                ResultDto::type eq type
            ).toList()
        )
    }

    suspend fun getAllResults(request: GetAllResultsRequest): ResultResponse {
        return ResultResponse(
            resultCollection.find(
                ResultDto::userLogin eq request.login
            ).toList()
        )
    }

    suspend fun resetResultsWithType(request: ResetResultsWithTypeRequest): Boolean {
        userCollection.findOne(UserDto::login eq request.login) ?: return false

        return resultCollection.deleteMany(
            ResultDto::userLogin eq request.login,
            ResultDto::type eq request.type
        )
            .wasAcknowledged()
    }

    /*
        Achieve
     */

    suspend fun getAllAchieves(): AchieveResponse {
        return AchieveResponse(
            achieveList = achieveCollection.find().toList()
        )
    }

    suspend fun getTypeAchieves(type: AchieveType): AchieveResponse {
        return AchieveResponse(
            achieveList = achieveCollection.find(AchieveDto::type eq type).toList()
        )
    }

    /*
        Events
     */

    suspend fun getAllEvents(): EventResponse {
        return EventResponse(
            eventCollection.find().toList()
        )
    }

    /*
        Map
     */

    suspend fun updateLabelOnMap(label: LabelRequest): Boolean {
        mapCollection.findOne(MapDto::id eq label.label.mapId) ?: return false
        return mapLabelCollection.updateOne(MapLabelDto::id eq label.label.id, label.label.toMapLabelDto())
            .wasAcknowledged()
    }

    suspend fun addLabelOnMap(label: LabelRequest): Boolean {
        mapCollection.findOne(MapDto::id eq label.label.mapId) ?: return false
        return mapLabelCollection.insertOne(label.label.toMapLabelDto()).wasAcknowledged()
    }

    suspend fun deleteLabelFromMap(id: String): Boolean =
        mapLabelCollection.deleteOne(MapLabelDto::id eq id).wasAcknowledged()


    suspend fun getMap(periodId: String): Map {
        val map = mapCollection.findOne(MapDto::periodId eq periodId)!!
        val labels = mapLabelCollection.find(MapLabelDto::mapId eq map.id).toList()

        return Map(
            id = map.id,
            map = map.map,
            labels = labels.map { it.toMapLabel() },
            periodId = map.periodId
        )
    }

    suspend fun getMapTypes(): List<MapType> {
        val types = mutableListOf<MapType>()
        MapCategory.entries.forEach { type ->
            types.add(getMapType(type))
        }
        return types.toList()
    }
    /*
        Additional
     */

    private suspend fun getMapType(type: MapCategory): MapType {
        val mapType = mapTypeCollection.findOne(MapTypeDto::type eq type)!!
        val periods = mapPeriodCollection.find(MapPeriodDto::mapTypeId eq mapType.id).toList()

        return MapType(
            id = mapType.id,
            title = mapType.title,
            description = mapType.description,
            type = mapType.type,
            periods = periods.map { it.toMapPeriod() }
        )
    }

    private suspend fun deleteDataFromTicket(ticketId: String) {
        achieveCollection.findOneAndDelete(AchieveDto::ownerId eq ticketId)
        tqCollection.find(TicketQuestionDto::ticketId eq ticketId).toList().let { tq ->
            tq.map { it.id }.forEach { tqId ->
                pqCollection.deleteMany(PracticeQuestionDto::tqId eq tqId)
            }
        }
        tqCollection.deleteMany(TicketQuestionDto::ticketId eq ticketId)
    }
}