package org.jukeboxmc.plugin

import org.cloudburstmc.protocol.bedrock.packet.SetTitlePacket
import org.jukeboxmc.api.JukeboxMC
import org.jukeboxmc.api.event.EventHandler
import org.jukeboxmc.api.event.Listener
import org.jukeboxmc.api.event.player.PlayerJoinEvent
import org.jukeboxmc.api.event.player.PlayerMoveEvent
import org.jukeboxmc.api.event.player.PlayerToggleSneakEvent
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.plugin.Plugin
import org.jukeboxmc.server.event.PacketSendEvent
import org.jukeboxmc.server.extensions.toJukeboxPlayer

class JukeboxMCPlugin : Plugin(), Listener {

    override fun onEnable() {
        this.getServer().getPluginManager().registerListener(this)
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.getPlayer()
        val inventory = player.getInventory()
        val armorInventory = player.getArmorInventory()

        armorInventory.setHelmet(Item.create(ItemType.NETHERITE_HELMET))
        armorInventory.setChestplate(Item.create(ItemType.NETHERITE_CHESTPLATE))
        armorInventory.setLeggings(Item.create(ItemType.NETHERITE_LEGGINGS))
        armorInventory.setBoots(Item.create(ItemType.NETHERITE_BOOTS))

        inventory.addItem(
            Item.create(ItemType.FURNACE, 1),
            Item.create(ItemType.BLAST_FURNACE, 1),
            Item.create(ItemType.COAL, 64),
            Item.create(ItemType.COAL, 64),
            Item.create(ItemType.IRON_ORE, 64),
            Item.create(ItemType.IRON_ORE, 64),
        )


        val packet = SetTitlePacket()
        packet.type = SetTitlePacket.Type.TITLE
        packet.text = "!&??Hello world"
        packet.fadeInTime = 10
        packet.stayTime = 70
        packet.fadeOutTime = 20
        packet.xuid = player.getXuid()
        packet.platformOnlineId = ""
        //player.toJukeboxPlayer().sendPacket(packet)

       /*
        inventory.addItem(
            Item.create(ItemType.GRINDSTONE, 1),
            Item.create(ItemType.WOODEN_SHOVEL, 1),
            Item.create(ItemType.WOODEN_SHOVEL, 1),
            Item.create(ItemType.STONE_SHOVEL, 1),
            Item.create(ItemType.STONE_SHOVEL, 1),
        )
        */
    }

    @EventHandler
    fun onPlayerSneak(event: PlayerToggleSneakEvent) {
        if (event.isSneaking()) {
            val packet = SetTitlePacket()
            packet.type = SetTitlePacket.Type.TITLE
            packet.text = "!&??Hello world"
            packet.fadeInTime = 10
            packet.stayTime = 70
            packet.fadeOutTime = 20
            packet.xuid = ""
            packet.platformOnlineId = ""
            //event.getPlayer().toJukeboxPlayer().sendPacket(packet)
        }
    }

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        val player = event.getPlayer()

        if (player.getLoadedChunk() == null) return

        val builder = StringBuilder()
        builder.append("§7TPS§8: §e").append(JukeboxMC.getServer().getTps()).append("\n")
        builder.append("§7ChunkX§8: §e").append(player.getLoadedChunk()!!.getX())
            .append(" §7ChunkZ§8: §e" + player.getLoadedChunk()!!.getZ()).append("\n")
        builder.append("§7Herzen§8: §e").append(player.getHealth())
            .append(" §7Direction§8: §e" + player.getDirection().name).append("\n")
        val itemInHand = player.getInventory().getItemInHand()
        builder.append("§7ItemInHand§8: §e").append(itemInHand.getType()).append(":")
            .append(itemInHand.getStackNetworkId()).append(" §7Meta§8: §e").append(itemInHand.getMeta()).append("\n")
        builder.append("§7ItemBlockId§8: §e" + itemInHand.getBlockNetworkId() + " ")
        builder.append("§7Durability§8: §e").append(itemInHand.getDurability()).append("\n")
        builder.append("§7Identiefer§8: §e").append(itemInHand.getIdentifier()).append("\n")
        builder.append("§7BlockType (0)§8: §e").append(player.getLocation().subtract(0f, 1f, 0f).getBlock().getType())
            .append("\n")
        builder.append("§7BlockType (1)§8: §e").append(player.getLocation().subtract(0f, 1f, 0f).getBlock(1).getType())
            .append("\n")
        builder.append("§7Saturation§8: §e").append(player.getSaturation()).append(" ").append("§7Hunger§8: §e")
            .append(player.getHunger()).append("\n")
        builder.append("§7Exhaustion§8: §e").append(player.getExhaustion()).append("\n")
        builder.append("§7Helmet§8: §e").append(player.getArmorInventory().getHelmet().getType()).append("\n")
        builder.append("§7Chestplate§8: §e").append(player.getArmorInventory().getChestplate().getType()).append("\n")
        builder.append("§7Leggings§8: §e").append(player.getArmorInventory().getLeggings().getType()).append("\n")
        builder.append("§7Boots§8: §e").append(player.getArmorInventory().getBoots().getType()).append("\n")
        player.sendTip(builder.toString())
    }
}