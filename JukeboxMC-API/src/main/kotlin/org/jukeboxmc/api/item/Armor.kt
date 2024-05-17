package org.jukeboxmc.api.item

import org.jukeboxmc.api.world.Sound

interface Armor : Item {

    fun getArmorTierType(): ArmorTierType

    fun getArmorPoints(): Int

    fun getEquipmentSound(): Sound

}