package org.jukeboxmc.api.event.player

import org.jukeboxmc.api.player.Player

class PlayerLoginEvent(player: Player) : PlayerEvent(player) {

    private var reason = "Disconnected"
    private var result = Result.ALLOWED

    fun getKickReason(): String {
        return this.reason
    }

    fun setKickReason(reason: String){
        this.reason = reason
    }

    fun setResult(result: Result) {
        this.result = result
    }

    fun getResult(): Result {
        return result
    }

    enum class Result {
        ALLOWED,
        SERVER_FULL,
        WHITELIST,
        XBOX_AUTHENTICATED,
        OTHER
    }
}