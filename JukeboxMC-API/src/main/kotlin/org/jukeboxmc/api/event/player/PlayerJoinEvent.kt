package org.jukeboxmc.api.event.player

import org.jukeboxmc.api.player.Player

class PlayerJoinEvent(
    player: Player,
    private var joinMessage: String? = null
) : PlayerEvent(player){

    fun getJoinMessage(): String? {
        return this.joinMessage
    }

    fun setJoinMessage(joinMessage: String?) {
        this.joinMessage = joinMessage
    }

}