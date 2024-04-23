package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.InventorySource
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.InventoryTransactionType
import org.cloudburstmc.protocol.bedrock.packet.InventoryTransactionPacket
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.event.block.BlockPlaceEvent
import org.jukeboxmc.api.event.player.PlayerDropItemEvent
import org.jukeboxmc.api.event.player.PlayerInteractEvent
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.AxisAlignedBB
import org.jukeboxmc.api.math.Location
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.entity.item.JukeboxEntityItem
import org.jukeboxmc.server.extensions.fromVector3f
import org.jukeboxmc.server.extensions.fromVector3i
import org.jukeboxmc.server.extensions.toJukeboxBlock
import org.jukeboxmc.server.extensions.toJukeboxItem
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.item.behavior.ItemBow
import org.jukeboxmc.server.player.JukeboxPlayer
import java.util.concurrent.TimeUnit

class InventoryTransactionHandler : PacketHandler<InventoryTransactionPacket> {

    override fun handle(packet: InventoryTransactionPacket, server: JukeboxServer, player: JukeboxPlayer) {
        if (packet.transactionType == InventoryTransactionType.ITEM_USE) {
            val itemInHand: Item = player.getInventory().getItemInHand()
            if (packet.actionType == 0) {
                val blockPosition = Vector().fromVector3i(packet.blockPosition, player.getDimension())
                val clickPosition = Vector().fromVector3f(packet.clickPosition, player.getDimension())
                val blockFace: BlockFace = BlockFace.fromId(packet.blockFace)!!
                val relativeBlock = player.getWorld().getBlock(blockPosition).getRelative(blockFace)

                val placeLocation = relativeBlock.getLocation()
                if (player.hasAction()) player.setAction(false)

                if (!this.useItemOn(player, blockPosition, placeLocation, clickPosition, blockFace)) {
                    val blockClicked = player.getWorld().getBlock(blockPosition).toJukeboxBlock()
                    blockClicked.update(player)

                    val replacedBlock =
                        player.getWorld().getBlock(blockPosition).getRelative(blockFace).toJukeboxBlock()
                    replacedBlock.update(player)
                }
            } else if (packet.actionType == 1) {
                val directionVector = player.getLocation().getDirection()
                val playerInteractEvent = PlayerInteractEvent(
                    player,
                    PlayerInteractEvent.Action.RIGHT_CLICK_AIR,
                    itemInHand,
                    directionVector
                )
                server.getPluginManager().callEvent(playerInteractEvent)
                if (playerInteractEvent.isCancelled()) {
                    player.getInventory().sendContents(player)
                    return
                }
                if (itemInHand.toJukeboxItem().useInAir(player, directionVector)) {
                    if (!player.hasAction()) {
                        player.setAction(true)
                        return
                    }
                    player.setAction(false)
                    if (!itemInHand.toJukeboxItem().onUse(player)) {
                        player.getInventory().sendContents(player)
                    }
                }
            } else if (packet.actionType == 2) {
                val block =
                    player.getWorld().getBlock(Vector().fromVector3i(packet.blockPosition, player.getDimension()))
                        .toJukeboxBlock()
                if (block.getType() == BlockType.AIR) return
                block.breakBlockHandling(player, itemInHand.toJukeboxItem())
            }
        } else if (packet.transactionType == InventoryTransactionType.NORMAL) {
            for (action in packet.actions) {
                if (action.source.type == InventorySource.Type.WORLD_INTERACTION) {
                    if (action.source.flag == InventorySource.Flag.DROP_ITEM) {
                        val slot = player.getInventory().getItemInHandSlot()
                        val playerDropItemEvent = PlayerDropItemEvent(player, JukeboxItem(action.toItem, true))
                        server.getPluginManager().callEvent(playerDropItemEvent)

                        if (playerDropItemEvent.isCancelled()) {
                            return
                        }

                        val entityItem = JukeboxEntityItem()
                        entityItem.setItem(playerDropItemEvent.getItem())
                        entityItem.setLocation(player.getLocation().add(0F, player.getEyeHeight(), 0F).clone())
                        entityItem.setMotion(player.getLocation().getDirection().multiply(0.4F, 0.4F, 0.4f))
                        entityItem.setThrower(player)
                        entityItem.setPickupDelay(1, TimeUnit.SECONDS)
                        entityItem.spawn()

                        player.getInventory().removeItem(slot, playerDropItemEvent.getItem(), 1)
                    }
                }
            }
        } else if (packet.transactionType == InventoryTransactionType.ITEM_USE_ON_ENTITY) {
            if(packet.actionType == 1) {
                player.getWorld().getEntityById(packet.runtimeEntityId)?.let {
                    if (player.attackWithItemInHand(it)) {
                        player.getInventory().getItemInHand().toJukeboxItem().updateDurability(player, 1)
                    }
                }
            }
        } else if (packet.transactionType == InventoryTransactionType.ITEM_RELEASE) {
            if (packet.actionType == 0) {
                if (player.getInventory().getItemInHand().getType() == ItemType.BOW) {
                    (player.getInventory().getItemInHand() as ItemBow).shoot(player)
                }
            }
            player.setAction(false)
        } else {
            player.setAction(false)
        }
    }


    fun useItemOn(
        player: JukeboxPlayer,
        blockLocation: Vector,
        placeLocation: Vector,
        clickedLocation: Vector,
        blockFace: BlockFace
    ): Boolean {
        var finalPlaceLocation = placeLocation
        val world = player.getWorld()
        val itemInHand = player.getInventory().getItemInHand().toJukeboxItem()
        val clickedBlock = world.getBlock(blockLocation).toJukeboxBlock()
        if (clickedBlock.getType() == BlockType.AIR) return false
        val replacedBlock = world.getBlock(finalPlaceLocation).toJukeboxBlock()
        val placedBlock: JukeboxBlock = itemInHand.toBlock().toJukeboxBlock()
        val playerInteractEvent = PlayerInteractEvent(
            player,
            if (clickedBlock.getType() == BlockType.AIR) PlayerInteractEvent.Action.RIGHT_CLICK_AIR else PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK,
            player.getInventory().getItemInHand(),
            clickedBlock
        )
        JukeboxServer.getInstance().getPluginManager().callEvent(playerInteractEvent)
        var interact = false
        if (!player.isSneaking()) {
            if (!playerInteractEvent.isCancelled()) {
                interact = clickedBlock.interact(player, world, blockLocation, clickedLocation, blockFace, itemInHand)
            }
        }

        val itemInteract = itemInHand.interact(player, blockFace, clickedLocation, clickedBlock)

        val location = Location(
            world,
            finalPlaceLocation.getX(),
            finalPlaceLocation.getY(),
            finalPlaceLocation.getZ(),
            player.getDimension()
        )
        placedBlock.setLocation(location)

        if (!interact && itemInHand.useOnBlock(player, clickedBlock, location)) {
            return true
        }

        if (itemInHand.getType() == ItemType.AIR || placedBlock.getType() == BlockType.AIR) {
            return interact
        }

        if (!interact && !itemInteract || player.isSneaking()) {
            if (clickedBlock.canBeReplaced(placedBlock)) {
                finalPlaceLocation = blockLocation
            } else if (!replacedBlock.canBeReplaced(placedBlock)) {
                return false
            }

            val boundingBox: AxisAlignedBB = player.getBoundingBox()
            if(!placedBlock.canPassThrough()) {
                if (placedBlock.getBoundingBox().intersectsWith(boundingBox)) {
                    return false
                }
            }

            if (player.getGameMode() == GameMode.SPECTATOR) {
                return false
            }
            val blockPlaceEvent = BlockPlaceEvent(placedBlock, replacedBlock, clickedBlock, player)
            JukeboxServer.getInstance().getPluginManager().callEvent(blockPlaceEvent)
            if (blockPlaceEvent.isCancelled()) {
                return false
            }
            val success: Boolean = blockPlaceEvent.getBlock().toJukeboxBlock().placeBlock(
                player,
                world, blockLocation, finalPlaceLocation, clickedLocation, itemInHand, blockFace
            )
            if (success) {
                if (player.getGameMode() == GameMode.SURVIVAL) {
                    val resultItem: Item = itemInHand.apply { this.setAmount(itemInHand.getAmount() - 1) }
                    if (itemInHand.getAmount() != 0) {
                        player.getInventory().setItemInHand(resultItem)
                    } else {
                        player.getInventory().setItemInHand(Item.create(ItemType.AIR))
                    }
                    player.getInventory().sendContents(
                        player.getInventory().getItemInHandSlot(),
                        player
                    )
                }
                placedBlock.playPlaceSound(finalPlaceLocation)
            }
            return success
        }
        return true
    }
}