package org.jukeboxmc.api.event.player

import org.jukeboxmc.api.math.Location
import org.jukeboxmc.api.player.Player

class PlayerRespawnEvent(
    player: Player,
    private var respawnLocation: Location
) : PlayerEvent(player) {

    fun getRespawnLocation(): Location {
        return this.respawnLocation
    }

    fun setRespawnLocation(respawnLocation: Location) {
        this.respawnLocation = respawnLocation
    }

}