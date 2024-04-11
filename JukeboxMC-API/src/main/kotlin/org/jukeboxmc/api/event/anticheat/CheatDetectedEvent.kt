package org.jukeboxmc.api.event.anticheat

import org.jukeboxmc.api.event.player.PlayerEvent
import org.jukeboxmc.api.player.Player

/**
 * @author Kaooot
 * @version 1.0
 */
class CheatDetectedEvent(
    player: Player,
    private val type: CheatType,
    private val reason: String,
    private var logReason: Boolean = true,
    private var notify: Boolean = true
) : PlayerEvent(player) {

    fun getReason() = this.reason

    fun getType() = this.type

    fun setLogReason(logReason: Boolean) {
        this.logReason = logReason
    }

    fun isLogReason() = this.logReason

    fun setNotify(notify: Boolean) {
        this.notify = notify
    }

    fun isNotify() = this.notify

    enum class CheatType {
        BLOCK_BREAKING,
        COMBAT,
        MOVEMENT
    }
}