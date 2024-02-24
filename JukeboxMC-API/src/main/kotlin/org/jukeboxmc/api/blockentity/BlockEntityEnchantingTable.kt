package org.jukeboxmc.api.blockentity

import org.jukeboxmc.api.inventory.EnchantmentTableInventory

interface BlockEntityEnchantingTable : BlockEntity {

    fun getEnchantmentTableInventory(): EnchantmentTableInventory

}