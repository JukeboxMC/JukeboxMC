package org.jukeboxmc.api.event.player

import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.player.Player

class PlayerExperienceChangeEvent(
    player: Player,
    private val oldExperience: Float,
    private val oldLevel: Int,
    private var newExperience: Float,
    private var newLevel: Int
) : PlayerEvent(player), Cancellable{

    fun getOldExperience(): Float {
        return this.oldExperience
    }

    fun getOldLevel(): Int {
        return this.oldLevel
    }

    fun getNewExperience(): Float {
        return this.newExperience
    }

    fun setNewExperience(newExperience: Float) {
        this.newExperience = newExperience
    }

    fun getNewLevel(): Int {
        return this.newLevel
    }

    fun setNewLevel(newLevel: Int) {
        this.newLevel = newLevel
    }
}