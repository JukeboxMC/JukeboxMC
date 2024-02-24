package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType
import org.jukeboxmc.api.inventory.EnchantmentTableInventory
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.server.blockentity.JukeboxBlockEntityEnchantingTable

class JukeboxEnchantmentTableInventory(inventoryHolder: InventoryHolder) : ContainerInventory(inventoryHolder, 2),
    EnchantmentTableInventory {

    override fun getInventoryHolder(): JukeboxBlockEntityEnchantingTable {
        return super.getInventoryHolder() as JukeboxBlockEntityEnchantingTable
    }

    override fun getType(): InventoryType {
        return InventoryType.ENCHANTMENT_TABLE
    }

    override fun getContainerType(): ContainerType {
        return ContainerType.ENCHANTMENT
    }
}