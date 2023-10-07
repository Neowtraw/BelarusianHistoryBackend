package com.codingub.di

import com.codingub.data.HistoryDatabase
import com.codingub.data.repositories.UserDataRepository
import com.codingub.data.repositories.UserDataRepositoryImpl
import org.koin.dsl.module

val koinModule = module {
    single { provideDatabase()}
    single { provideUserDataRepository(get() as HistoryDatabase) }
}

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