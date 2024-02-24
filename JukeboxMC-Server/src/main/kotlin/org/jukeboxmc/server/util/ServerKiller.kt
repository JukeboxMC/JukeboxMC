package org.jukeboxmc.server.util

import org.jukeboxmc.api.logger.Logger
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

class ServerKiller(private val logger: Logger) : Thread() {

    override fun run() {
        try {
            sleep(TimeUnit.SECONDS.toMillis(3))
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
        logger.info("Server shutdown successfully!")
        exitProcess(1)
    }
}