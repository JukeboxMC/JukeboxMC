package org.jukeboxmc.api.item

import org.jukeboxmc.api.world.Sound

interface Armor {

    fun getArmorTierType(): ArmorTierType

    fun getArmorPoints(): Int

    fun getEquipmentSound(): Sound

}