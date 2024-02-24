package org.jukeboxmc.api.event.player

import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.player.Player

class PlayerFoodLevelChangeEvent(
        player: Player,
        private var foodLevel: Int,
        private val saturationLevel: Float
) : PlayerEvent(player), Cancellable {

    fun getFoodLevel(): Int {
        return this.foodLevel
    }

    fun setFoodLevel(value: Int) {
        this.foodLevel = value
    }

    fun getSaturationLevel(): Float {
        return this.saturationLevel
    }

}