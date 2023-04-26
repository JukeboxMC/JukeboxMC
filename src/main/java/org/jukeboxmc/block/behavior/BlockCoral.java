package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.CoralColor;
import org.jukeboxmc.block.data.UpdateReason;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.event.block.BlockFadeEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemCoral;
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
        Block layer0 = world.getBlock(placePosition);

        if (layer0 instanceof BlockWater blockWater) {
            if (blockWater.getLiquidDepth() != 0) {
                return false;
            }
            world.setBlock(placePosition, Block.create(BlockType.WATER), 1, placePosition.getDimension(), false);
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
                blockCoral.setDead(true, false);
                BlockFadeEvent blockFadeEvent = new BlockFadeEvent(this, blockCoral);
                Server.getInstance().getPluginManager().callEvent(blockFadeEvent);
                if (!blockFadeEvent.isCancelled()) {
                    this.setDead(true, true);
                }
            }
        }
        return -1;
    }

    @Override
    public Item toItem() {
        return Item.<ItemCoral>create(ItemType.CORAL).setCoralColor(this.getCoralColor()).setDead(this.isDead());
    }

    @Override
    public int getWaterLoggingLevel() {
        return 2;
    }

    public BlockCoral setCoralColor(CoralColor coralColor) {
        return this.setState("coral_color", coralColor.name().toLowerCase());
    }

    public CoralColor getCoralColor() {
        return this.stateExists("coral_color") ? CoralColor.valueOf(this.getStringState("coral_color")) : CoralColor.BLUE;
    }

    public BlockCoral setDead(boolean value) {
        return this.setDead(value, true);
    }

    public BlockCoral setDead(boolean value, boolean update) {
        return this.setState("dead_bit", value ? (byte) 1 : (byte) 0, update);
    }

    public boolean isDead() {
        return this.stateExists("dead_bit") && this.getByteState("dead_bit") == 1;
    }
}
