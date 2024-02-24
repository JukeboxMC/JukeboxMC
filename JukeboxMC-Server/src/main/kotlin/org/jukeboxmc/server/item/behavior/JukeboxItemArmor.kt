package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.item.Armor
import org.jukeboxmc.api.item.Durability
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer

abstract class JukeboxItemArmor(itemType: ItemType, countNetworkId: Boolean) : JukeboxItem(itemType, countNetworkId), Armor, Durability {

    override fun useInAir(player: JukeboxPlayer, clickVector: Vector): Boolean {
        return true
    }

}