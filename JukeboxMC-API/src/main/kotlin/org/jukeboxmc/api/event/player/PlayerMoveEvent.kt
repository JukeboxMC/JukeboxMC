package org.jukeboxmc.api.event.player

import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.math.Location
import org.jukeboxmc.api.player.Player

class PlayerMoveEvent(
    player: Player,
    private var from: Location,
    private var to: Location
) : PlayerEvent(player), Cancellable {

    fun getFrom(): Location {
        return this.from
    }

    fun setFrom(from: Location) {
        this.from = from
    }

    fun getTo(): Location {
        return this.to
    }

    fun setTo(to: Location) {
        this.to = to
    }

}