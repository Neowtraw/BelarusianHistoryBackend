package com.codingub.di

import com.codingub.data.HistoryDatabase
import com.codingub.data.repositories.*
import com.codingub.security.hashing.HashingService
import com.codingub.security.hashing.SHA256HashingService
import com.codingub.security.token.JwtTokenService
import com.codingub.security.token.TokenService
import org.koin.dsl.module

val koinModule = module {
    single { provideDatabase()}
    single { provideHashingService() }
    single { provideTokenService() }

    single { provideUserDataRepository(get() as HistoryDatabase) }
    single { provideTicketDataRepository(get() as HistoryDatabase) }
    single { provideTqDataRepository(get() as HistoryDatabase) }
    single { providePqDataRepository(get() as HistoryDatabase) }
    single { provideAchieveDataRepository(get() as HistoryDatabase) }
    single { provideGroupDataRepository(get() as HistoryDatabase)}
    single { provideEventDataRepository(get() as HistoryDatabase)}
    single { provideMapDataRepository(get() as HistoryDatabase)}

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

internal fun provideEventDataRepository(
    database: HistoryDatabase
) : EventDataRepository = EventDataRepositoryImpl(database)
internal fun providePqDataRepository(
    database: HistoryDatabase
) : PqDataRepository = PqDataRepositoryImpl(database)
internal fun provideUserDataRepository(
    database: HistoryDatabase
) : UserDataRepository = UserDataRepositoryImpl(database)

internal fun provideTicketDataRepository(
    database: HistoryDatabase
) : TicketDataRepository = TicketDataRepositoryImpl(database)
internal fun provideTqDataRepository(
    database: HistoryDatabase
) : TqDataRepository = TqDataRepositoryImpl(database)
internal fun provideAchieveDataRepository(
    database: HistoryDatabase
) : AchieveDataRepository = AchieveDataRepositoryImpl(database)
internal fun provideGroupDataRepository(
    database: HistoryDatabase
) : GroupDataRepository = GroupDataRepositoryImpl(database)
internal fun provideMapDataRepository(
    database: HistoryDatabase
) : MapRepository = MapRepositoryImpl(database)
