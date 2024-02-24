package org.jukeboxmc.api.event.player

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.Player

class PlayerInteractEvent(
    player: Player,
    private val action: Action,
    private var item: Item,
    private val touchVector: Vector
) : PlayerEvent(player), Cancellable {

    private var clickedBlock: Block?

    init {
        clickedBlock = null
    }

    constructor(player: Player, action: Action, item: Item, clickedBlock: Block) : this(
        player,
        action,
        item,
        clickedBlock.getLocation()
    ) {
        this.clickedBlock = clickedBlock
    }

    fun getAction(): Action {
        return action
    }

    fun getItem(): Item {
        return item
    }

    fun getClickedBlock(): Block? {
        return clickedBlock
    }

    fun getTouchVector(): Vector {
        return touchVector.clone()
    }

    enum class Action {
        LEFT_CLICK_AIR,
        RIGHT_CLICK_AIR,
        LEFT_CLICK_BLOCK,
        RIGHT_CLICK_BLOCK,
        PHYSICAL
    }
}