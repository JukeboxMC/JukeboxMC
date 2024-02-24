package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.packet.InventoryContentPacket
import org.cloudburstmc.protocol.bedrock.packet.InventorySlotPacket
import org.cloudburstmc.protocol.bedrock.packet.MobArmorEquipmentPacket
import org.jukeboxmc.api.inventory.ArmorInventory
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.api.item.Armor
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.world.Sound
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.entity.passive.JukeboxEntityHuman
import org.jukeboxmc.server.extensions.toJukeboxItem
import org.jukeboxmc.server.extensions.toJukeboxPlayer
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer

class JukeboxArmorInventory(
    inventoryHolder: InventoryHolder
) : ContainerInventory(inventoryHolder, 4), ArmorInventory {

    override fun getInventoryHolder(): JukeboxEntityHuman {
        return super.getInventoryHolder() as JukeboxEntityHuman
    }

    override fun getType(): InventoryType {
        return InventoryType.ARMOR
    }

    override fun sendContents(player: JukeboxPlayer) {
        if (this.getInventoryHolder() == player) {
            val inventoryContentPacket = InventoryContentPacket()
            inventoryContentPacket.containerId = WindowId.ARMOR_DEPRECATED.getId()
            inventoryContentPacket.contents = this.getItemDataContents()
            player.sendPacket(inventoryContentPacket)
        } else {
            this.sendMobArmor(player)
        }
    }

    override fun sendContents(slot: Int, player: JukeboxPlayer) {
        if (this.getInventoryHolder() == player) {
            val inventorySlotPacket = InventorySlotPacket()
            inventorySlotPacket.containerId = WindowId.ARMOR_DEPRECATED.getId()
            inventorySlotPacket.slot = slot
            inventorySlotPacket.item = this.getContents()[slot].toJukeboxItem().toItemData()
            player.sendPacket(inventorySlotPacket)
        } else {
            this.sendMobArmor(player)
        }
    }

    override fun setItem(slot: Int, item: Item, sendContents: Boolean) {
        val oldItem = this.getContents()[slot]
        super.setItem(slot, item, sendContents)
        val inventoryHolder = this.getInventoryHolder()
        if (inventoryHolder is JukeboxPlayer) {
            val armorInventory: ArmorInventory = inventoryHolder.getArmorInventory()
            val mobArmorEquipmentPacket = MobArmorEquipmentPacket()
            mobArmorEquipmentPacket.runtimeEntityId = this.getHolderId()
            mobArmorEquipmentPacket.helmet = armorInventory.getHelmet().toJukeboxItem().toItemData()
            mobArmorEquipmentPacket.chestplate = armorInventory.getChestplate().toJukeboxItem().toItemData()
            mobArmorEquipmentPacket.leggings = armorInventory.getLeggings().toJukeboxItem().toItemData()
            mobArmorEquipmentPacket.boots = armorInventory.getBoots().toJukeboxItem().toItemData()
            for (onlinePlayer in JukeboxServer.getInstance().getOnlinePlayers()) {
                if (onlinePlayer.toJukeboxPlayer() == inventoryHolder) {
                    val inventorySlotPacket = InventorySlotPacket()
                    inventorySlotPacket.containerId = WindowId.ARMOR_DEPRECATED.getId()
                    inventorySlotPacket.slot = slot
                    inventorySlotPacket.item = this.getContents()[slot].toJukeboxItem().toItemData()
                    onlinePlayer.toJukeboxPlayer().sendPacket(inventorySlotPacket)
                    onlinePlayer.toJukeboxPlayer().sendPacket(mobArmorEquipmentPacket)
                } else {
                    onlinePlayer.toJukeboxPlayer().sendPacket(mobArmorEquipmentPacket)
                }
            }
            if (item is Armor && oldItem.getType() == ItemType.AIR) {
                inventoryHolder.playSound(item.getEquipmentSound(), 1F, 1F)
            }
        }
    }

    override fun getHelmet(): Item {
        return this.getContents()[0]
    }

    override fun setHelmet(item: Item) {
        this.setItem(0, item)
    }

    override fun getChestplate(): Item {
        return this.getContents()[1]
    }

    override fun setChestplate(item: Item) {
        this.setItem(1, item)
    }

    override fun getLeggings(): Item {
        return this.getContents()[2]
    }

    override fun setLeggings(item: Item) {
        this.setItem(2, item)
    }

    override fun getBoots(): Item {
        return this.getContents()[3]
    }

    override fun setBoots(item: Item) {
        this.setItem(3, item)
    }

    fun getTotalArmorValue(): Float {
        var armorValue = 0f
        for (item in this.getContents()) {
            if (item is Armor) {
                armorValue += item.getArmorPoints().toFloat()
            }
        }
        return armorValue
    }

    fun damageEvenly(finalDamage: Float) {
        var damage: Float = finalDamage
        damage /= 4.0f
        if (damage < 1.0f) {
            damage = 1.0f
        }
        val holder = this.getInventoryHolder()
        if (holder is JukeboxPlayer) {
            val helmet = this.getHelmet() as JukeboxItem
            if (helmet.getType() != ItemType.AIR) {
                if (helmet.calculateDurability(damage.toInt())) {
                    this.setHelmet(Item.create(ItemType.AIR))
                    holder.playSound(Sound.RANDOM_BREAK, 1F, 1F)
                } else {
                    this.setHelmet(helmet)
                }
            }
            val chestplate = getChestplate() as JukeboxItem
            if (chestplate.getType() != ItemType.AIR) {
                if (chestplate.calculateDurability(damage.toInt())) {
                    this.setChestplate(Item.create(ItemType.AIR))
                    holder.playSound(Sound.RANDOM_BREAK, 1F, 1F)
                } else {
                    this.setChestplate(chestplate)
                }
            }
            val leggings = getLeggings() as JukeboxItem
            if (leggings.getType() != ItemType.AIR) {
                if (leggings.calculateDurability(damage.toInt())) {
                    this.setLeggings(Item.create(ItemType.AIR))
                    holder.playSound(Sound.RANDOM_BREAK, 1F, 1F)
                } else {
                    this.setLeggings(leggings)
                }
            }
            val boots = getBoots()  as JukeboxItem
            if (boots.getType() != ItemType.AIR) {
                if (boots.calculateDurability(damage.toInt())) {
                    this.setBoots(Item.create(ItemType.AIR))
                    holder.playSound(Sound.RANDOM_BREAK, 1F, 1F)
                } else {
                    this.setBoots(boots)
                }
            }
        }
    }

    private fun sendMobArmor(player: JukeboxPlayer) {
        val mobArmorEquipmentPacket = MobArmorEquipmentPacket()
        mobArmorEquipmentPacket.runtimeEntityId = getInventoryHolder().getEntityId()
        mobArmorEquipmentPacket.helmet = this.getContents()[0].toJukeboxItem().toItemData()
        mobArmorEquipmentPacket.chestplate = this.getContents()[1].toJukeboxItem().toItemData()
        mobArmorEquipmentPacket.leggings = this.getContents()[2].toJukeboxItem().toItemData()
        mobArmorEquipmentPacket.boots = this.getContents()[3].toJukeboxItem().toItemData()
        player.sendPacket(mobArmorEquipmentPacket)
    }
}