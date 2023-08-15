package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.UpdateReason;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.event.block.BlockFadeEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCoral extends Block implements Waterlogable {

    public BlockCoral(Identifier identifier) {
        super(identifier);
    }

    public BlockCoral(Identifier identifier, NbtMap blockStates) {
        super(identifier, blockStates);
    }

    @Override
    public boolean placeBlock(Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace) {
        Block block = world.getBlock(placePosition);
        if (block instanceof BlockWater blockWater && blockWater.getLiquidDepth() == 0) {
            world.setBlock(placePosition, Block.create(BlockType.WATER), 1, false);
        }
        if (this.getSide(BlockFace.DOWN).isSolid()) {
            world.setBlock(placePosition, this);
            return true;
        }
        return false;
    }

    @Override
    public long onUpdate(UpdateReason updateReason) {
        World world = this.location.getWorld();
        if (updateReason.equals(UpdateReason.NORMAL)) {
            Block blockDown = this.getSide(BlockFace.DOWN);
            if (!blockDown.isSolid()) {
                world.setBlock(this.location, Block.create(BlockType.AIR));
                world.sendLevelEvent(this.location, LevelEvent.PARTICLE_DESTROY_BLOCK, this.runtimeId);
                this.playBreakSound();
            } else if (!this.isDead()) {
                world.scheduleBlockUpdate(this, 60 + ThreadLocalRandom.current().nextInt(40));
            }
            return -1;
        } else if (updateReason.equals(UpdateReason.SCHEDULED)) {
            Block block = world.getBlock(this.location, 1);
            if (!this.isDead() && !(block instanceof BlockWater) && !(block.getType().equals(BlockType.FROSTED_ICE))) {
                BlockCoral blockCoral = (BlockCoral) super.clone();
                blockCoral.setDead();
                BlockFadeEvent blockFadeEvent = new BlockFadeEvent(this, blockCoral);
                Server.getInstance().getPluginManager().callEvent(blockFadeEvent);
                if (!blockFadeEvent.isCancelled()) {
                    this.setDead();
                }
            }
        }
        return -1;
    }

    @Override
    public int getWaterLoggingLevel() {
        return 2;
    }

    public boolean isDead() {
        return this.blockType.equals(BlockType.DEAD_FIRE_CORAL) ||
                this.blockType.equals(BlockType.DEAD_TUBE_CORAL) ||
                this.blockType.equals(BlockType.DEAD_HORN_CORAL) ||
                this.blockType.equals(BlockType.DEAD_BUBBLE_CORAL) ||
                this.blockType.equals(BlockType.DEAD_BRAIN_CORAL);
    }

    public void setDead() {
        if (this.blockType.equals(BlockType.FIRE_CORAL)) {
            this.getWorld().setBlock(this.location, Block.create(BlockType.DEAD_FIRE_CORAL));
        } else if (this.blockType.equals(BlockType.TUBE_CORAL)) {
            this.getWorld().setBlock(this.location, Block.create(BlockType.DEAD_TUBE_CORAL));
        } else if (this.blockType.equals(BlockType.HORN_CORAL)) {
            this.getWorld().setBlock(this.location, Block.create(BlockType.DEAD_HORN_CORAL));
        } else if (this.blockType.equals(BlockType.BUBBLE_CORAL)) {
            this.getWorld().setBlock(this.location, Block.create(BlockType.DEAD_BUBBLE_CORAL));
        } else if (this.blockType.equals(BlockType.BRAIN_CORAL)) {
            this.getWorld().setBlock(this.location, Block.create(BlockType.DEAD_BRAIN_CORAL));
        }
    }
}
