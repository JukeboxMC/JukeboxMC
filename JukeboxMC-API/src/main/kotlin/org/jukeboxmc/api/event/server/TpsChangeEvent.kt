package org.jukeboxmc.api.event.server

import org.jukeboxmc.api.Server

class TpsChangeEvent(
    server: Server,
    private val lastTps: Long,
    private val currentTps: Long
) : ServerEvent(server) {

    fun getLastTps(): Long {
        return this.lastTps
    }

    fun getCurrentTps(): Long {
        return this.currentTps
    }

}