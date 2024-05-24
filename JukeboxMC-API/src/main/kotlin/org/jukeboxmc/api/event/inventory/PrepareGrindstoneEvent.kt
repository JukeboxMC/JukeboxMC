package org.jukeboxmc.api.event.inventory

import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.inventory.Inventory
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.player.Player

class PrepareGrindstoneEvent(
    inventory: Inventory,
    private val player: Player,
    private val input: Item,
    private val additional: Item,
    private val result: Item,
    private var experience: Float
) : InventoryEvent(inventory), Cancellable {

    fun getPlayer(): Player {
        return this.player
    }

    fun getInput(): Item {
        return this.input
    }

    fun getAdditional(): Item {
        return this.additional
    }

    fun getResult(): Item {
        return this.result
    }

    fun getExperience(): Float {
        return this.experience
    }

    fun setExperience(experience: Float) {
        this.experience = experience
    }

}