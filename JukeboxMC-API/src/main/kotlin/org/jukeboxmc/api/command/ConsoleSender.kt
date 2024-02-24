package org.jukeboxmc.api.command

import org.jukeboxmc.api.JukeboxMC
import org.jukeboxmc.api.Server

class ConsoleSender : CommandSender {

    override fun sendMessage(message: String) {
        JukeboxMC.getServer().getLogger().info(message)
    }

    override fun hasPermission(permission: String): Boolean {
        return true
    }

    override fun getServer(): Server {
        return JukeboxMC.getServer()
    }
}