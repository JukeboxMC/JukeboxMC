package org.jukeboxmc.server.item.behavior

import org.cloudburstmc.protocol.bedrock.data.SoundEvent
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.event.player.PlayerBucketEmptyEvent
import org.jukeboxmc.api.event.player.PlayerBucketFillEvent
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Location
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.block.behavior.BlockLava
import org.jukeboxmc.server.block.behavior.BlockLiquid
import org.jukeboxmc.server.extensions.toJukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer

open class ItemBucket(itemType: ItemType, countNetworkId: Boolean) : JukeboxItem(itemType, countNetworkId) {

    override fun useOnBlock(player: JukeboxPlayer, block: Block, placeLocation: Location): Boolean {
        var blockValue = block
        if (block !is BlockLiquid && !block.getType().equals(BlockType.POWDER_SNOW)) {
            blockValue = player.getWorld().getBlock(block.getLocation(), 1)
        }

        if (blockValue is BlockLiquid || blockValue.getType().equals(BlockType.POWDER_SNOW)) {
            if (getType() !== ItemType.BUCKET) {
                return false
            }
            val item = if (blockValue.getType() == BlockType.POWDER_SNOW
            ) Item.create(ItemType.POWDER_SNOW_BUCKET) else if (blockValue is BlockLava) Item.create(ItemType.LAVA_BUCKET) else Item.create(
                ItemType.WATER_BUCKET
            )
            val playerBucketFillEvent = PlayerBucketFillEvent(player, this, item, blockValue)
            JukeboxServer.getInstance().getPluginManager().callEvent(playerBucketFillEvent)
            if (playerBucketFillEvent.isCancelled()) {
                player.getInventory().sendContents(player)
                return false
            }
            player.getWorld().setBlock(blockValue.getLocation(), Block.create(BlockType.AIR))
            if (blockValue.getType() == BlockType.POWDER_SNOW) {
                player.getWorld().playLevelSound(player.getLocation(), SoundEvent.BUCKET_FILL_POWDER_SNOW)
            } else if (blockValue is BlockLava) {
                player.getWorld().playLevelSound(player.getLocation(), SoundEvent.BUCKET_FILL_LAVA)
            } else {
                player.getWorld().playLevelSound(player.getLocation(), SoundEvent.BUCKET_FILL_WATER)
            }
            if (player.getGameMode() != GameMode.CREATIVE) {
                if (getAmount() - 1 <= 0) {
                    player.getInventory().setItemInHand(playerBucketFillEvent.getItemInHand())
                } else {
                    val clone: Item = clone()
                    clone.setAmount(getAmount() - 1)
                    player.getInventory().setItemInHand(clone)
                    if (player.getInventory().addItem(playerBucketFillEvent.getItemInHand()).isNotEmpty()) {
                        player.getWorld().dropItem(player.getLocation(), item)
                    }
                }
            }
        } else {
            blockValue = blockValue.getLocation().getWorld().getBlock(blockValue.getLocation(), 0)
            val placedBlock: Block
            when (getType()) {
                ItemType.BUCKET, ItemType.MILK_BUCKET, ItemType.COD_BUCKET, ItemType.SALMON_BUCKET, ItemType.PUFFERFISH_BUCKET, ItemType.TROPICAL_FISH_BUCKET, ItemType.AXOLOTL_BUCKET -> {
                    return false
                }

                else -> {
                    placedBlock = this.toBlock().toJukeboxBlock()
                    if (blockValue.getWaterLoggingLevel() > 0 && getType() === ItemType.WATER_BUCKET) {
                        placedBlock.setLocation(blockValue.getLocation())
                        placedBlock.setLayer(1)
                    } else if (blockValue is BlockLiquid) {
                        return false
                    } else {
                        placedBlock.setLocation(placeLocation)
                    }
                }
            }
            val playerBucketEmptyEvent = PlayerBucketEmptyEvent(
                player, this,
                Item.create(ItemType.BUCKET), blockValue, placedBlock
            )
            JukeboxServer.getInstance().getPluginManager().callEvent(playerBucketEmptyEvent)
            if (playerBucketEmptyEvent.isCancelled()) {
                player.getInventory().sendContents(player)
                return false
            }
            if (placedBlock.getType() == BlockType.POWDER_SNOW) {
                player.getWorld().playLevelSound(player.getLocation(), SoundEvent.BUCKET_EMPTY_POWDER_SNOW)
            } else if (placedBlock is BlockLava) {
                player.getWorld().playLevelSound(player.getLocation(), SoundEvent.BUCKET_EMPTY_LAVA)
            } else {
                player.getWorld().playLevelSound(player.getLocation(), SoundEvent.BUCKET_EMPTY_WATER)
            }
            player.getWorld().setBlock(placeLocation.getBlockX(), placeLocation.getBlockY(), placeLocation.getBlockZ(), placedBlock.getLayer(), placeLocation.getDimension(), placedBlock, false)
            if (placedBlock is BlockLiquid) {
                player.getWorld().scheduleBlockUpdate(placedBlock, placedBlock.tickRate())
            }
            if (player.getGameMode() !== GameMode.CREATIVE) {
                if (getAmount() - 1 <= 0) {
                    player.getInventory().setItemInHand(playerBucketEmptyEvent.getItemInHand())
                } else {
                    val clone: Item = clone()
                    clone.setAmount(getAmount() - 1)
                    player.getInventory().setItemInHand(clone)
                    if (player.getInventory().addItem(playerBucketEmptyEvent.getItemInHand()).isNotEmpty()) {
                        player.getWorld().dropItemNaturally(player.getLocation(), Item.create(ItemType.BUCKET))
                    }
                }
            }
        }
        return true
    }

}