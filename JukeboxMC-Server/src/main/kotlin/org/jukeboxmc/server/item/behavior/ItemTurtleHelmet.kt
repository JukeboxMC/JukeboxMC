package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.item.ArmorTierType
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.world.Sound

class ItemTurtleHelmet(itemType: ItemType, countNetworkId: Boolean) : JukeboxItemArmor(itemType, countNetworkId) {

    override fun getArmorTierType(): ArmorTierType {
        return ArmorTierType.TURTLE
    }

    override fun getArmorPoints(): Int {
        return 2
    }

    override fun getEquipmentSound(): Sound {
        return Sound.ARMOR_EQUIP_GENERIC
    }

    override fun getMaxDurability(): Int {
        return 275
    }

}