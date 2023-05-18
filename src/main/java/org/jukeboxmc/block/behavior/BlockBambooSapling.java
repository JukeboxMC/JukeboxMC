package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.BambooLeafSize;
import org.jukeboxmc.block.data.UpdateReason;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.event.block.BlockGrowEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

import java.util.concurrent.ThreadLocalRandom;

public class BlockBambooSapling extends Block {

    public BlockBambooSapling(Identifier identifier) {
        super(identifier);
    }

    public BlockBambooSapling(Identifier identifier, NbtMap blockStates) {
        super(identifier, blockStates);
    }

    @Override
    public boolean placeBlock(Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace) {
        Block blockDown = this.getSide(BlockFace.DOWN);
        if (!blockDown.getType().equals(BlockType.GRASS) &&
                !blockDown.getType().equals(BlockType.DIRT) &&
                !blockDown.getType().equals(BlockType.SAND) &&
                !blockDown.getType().equals(BlockType.GRAVEL) &&
                !blockDown.getType().equals(BlockType.PODZOL)) {
            return false;
        }

        if (world.getBlock(placePosition) instanceof BlockWater || world.getBlock(placePosition, 1) instanceof BlockWater) {
            return false;
        }
        world.setBlock(placePosition, this);
        return true;
    }

    @Override
    public boolean interact(Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand) {
        if (itemInHand.getType().equals(ItemType.BONE_MEAL)) {
            boolean success = false;
            Block block = this.getSide(BlockFace.UP);
            if (block.getType().equals(BlockType.AIR)) {
                success = this.grow(block);
            }

            if (success) {
                itemInHand.updateItem(player, 1);
                this.location.getWorld().spawnParticle( LevelEvent.PARTICLE_CROP_GROWTH, this.location );
            }
            return true;
        }
        return false;
    }

    @Override
    public long onUpdate(UpdateReason updateReason) {
        if (updateReason.equals(UpdateReason.NORMAL)) {
            Block blockDown = this.getSide(BlockFace.DOWN);
            if (!blockDown.getType().equals(BlockType.GRASS) &&
                    !blockDown.getType().equals(BlockType.DIRT) &&
                    !blockDown.getType().equals(BlockType.SAND) &&
                    !blockDown.getType().equals(BlockType.GRAVEL) &&
                    !blockDown.getType().equals(BlockType.PODZOL)) {
                this.breakNaturally();
            } else {
                Block blockUp = this.getSide(BlockFace.UP);
                if (blockUp.getType().equals(BlockType.BAMBOO)) {
                    BlockBamboo blockBambooUp = (BlockBamboo) blockUp;
                    BlockBamboo blockBamboo = Block.create(BlockType.BAMBOO);
                    blockBamboo.setBambooStalkThickness(blockBambooUp.getBambooStalkThickness());
                    this.location.getWorld().setBlock(this.location, blockBamboo);
                }
            }
            return 0;
        } else if (updateReason.equals(UpdateReason.RANDOM)) {
            Block blockUp = this.getSide(BlockFace.UP);
            if (!this.hasAge() && blockUp.getType().equals(BlockType.AIR) && ThreadLocalRandom.current().nextInt(3) == 0) {
                BlockBamboo blockBamboo = Block.create(BlockType.BAMBOO);
                blockBamboo.setBambooLeafSize(BambooLeafSize.SMALL_LEAVES, false);
                BlockGrowEvent blockGrowEvent = new BlockGrowEvent(blockUp, blockBamboo);
                Server.getInstance().getPluginManager().callEvent(blockGrowEvent);
                if (!blockGrowEvent.isCancelled()) {
                    BlockBamboo block = (BlockBamboo) blockGrowEvent.getNewState();
                    block.setLocation(new Location(this.location.getWorld(), this.location.getBlockX(), blockUp.getLocation().getBlockY(), this.location.getBlockZ()));
                    block.placeBlock(null, this.location.getWorld(), null, block.getLocation(), new Vector(0.5f, 0.5f, 0.5f), Item.create(ItemType.AIR), BlockFace.DOWN);
                }
            }
        }
        return 0;
    }

    public boolean grow(Block block) {
        BlockBamboo blockBamboo = Block.create(BlockType.BAMBOO);
        blockBamboo.setLocation(this.location);
        return blockBamboo.grow(block);
    }

    public void setAge(boolean value) {
        this.setState("age_bit", value ? (byte) 1 : (byte) 0);
    }

    public void setAge(boolean value, boolean update) {
        this.setState("age_bit", value ? (byte) 1 : (byte) 0, update);
    }

    public boolean hasAge() {
        return this.stateExists("age_bit") && this.getByteState("age_bit") == 1;
    }
}
