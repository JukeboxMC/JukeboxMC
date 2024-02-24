package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.item.ArmorTierType
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.world.Sound
import org.jukeboxmc.server.player.JukeboxPlayer

class ItemGoldenHelmet(itemType: ItemType, countNetworkId: Boolean) : JukeboxItemArmor(itemType, countNetworkId) {

    override fun useInAir(player: JukeboxPlayer, clickVector: Vector): Boolean {
        val oldItem = player.getArmorInventory().getHelmet()
        player.getArmorInventory().setHelmet(this)
        player.getInventory().setItemInHand(oldItem)
        return super.useInAir(player, clickVector)
    }

    override fun getArmorTierType(): ArmorTierType {
        return ArmorTierType.GOLD
    }

    override fun getArmorPoints(): Int {
        return 2
    }

    override fun getEquipmentSound(): Sound {
        return Sound.ARMOR_EQUIP_GOLD
    }

    override fun getMaxDurability(): Int {
        return 77
    }
}