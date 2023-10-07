package com.codingub.utils

import org.slf4j.LoggerFactory

object HistoryLogger {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun createLogger(type: Class<*>){
        val newLogger = LoggerFactory.getLogger(type)
    }

    fun info(message: String) = logger.info(message)
    fun debug(message: String) = logger.debug(message)
    fun error(message: String) = logger.error(message)
    fun warn(message: String) = logger.warn(message)
}