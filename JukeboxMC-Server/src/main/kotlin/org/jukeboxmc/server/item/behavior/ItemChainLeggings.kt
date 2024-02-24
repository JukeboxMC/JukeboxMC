package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.item.ArmorTierType
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.world.Sound
import org.jukeboxmc.server.player.JukeboxPlayer

class ItemChainLeggings(itemType: ItemType, countNetworkId: Boolean) : JukeboxItemArmor(itemType, countNetworkId) {

    override fun useInAir(player: JukeboxPlayer, clickVector: Vector): Boolean {
        val oldItem = player.getArmorInventory().getLeggings()
        player.getArmorInventory().setLeggings(this)
        player.getInventory().setItemInHand(oldItem)
        return super.useInAir(player, clickVector)
    }

    override fun getArmorTierType(): ArmorTierType {
        return ArmorTierType.CHAIN
    }

    override fun getArmorPoints(): Int {
        return 4
    }

    override fun getEquipmentSound(): Sound {
        return Sound.ARMOR_EQUIP_CHAIN
    }

    override fun getMaxDurability(): Int {
        return 225
    }
}