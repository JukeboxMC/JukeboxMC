package org.jukeboxmc.api.event.server

import org.jukeboxmc.api.Server
import org.jukeboxmc.api.event.Event

abstract class ServerEvent(private val server: Server) : Event() {

    fun getServer(): Server {
        return this.server
    }
}