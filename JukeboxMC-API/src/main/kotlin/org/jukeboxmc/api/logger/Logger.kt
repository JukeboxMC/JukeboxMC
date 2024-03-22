package org.jukeboxmc.api.logger

import lombok.extern.log4j.Log4j2
import org.apache.logging.log4j.LogManager


@Log4j2
class Logger(private val logManager: org.apache.logging.log4j.Logger = LogManager.getLogger(Logger::class.java)) {

    fun info(message: String?) {
        logManager.info(message)
    }

    fun error(message: String?) {
        logManager.error(message)
    }

    fun warn(message: String?) {
        logManager.warn(message)
    }
}

