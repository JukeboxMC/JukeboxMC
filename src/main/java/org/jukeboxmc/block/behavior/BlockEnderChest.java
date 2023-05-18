package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.blockentity.BlockEntityEnderChest;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEnderChest extends Block implements Waterlogable {

    public BlockEnderChest(Identifier identifier) {
        super(identifier);
    }

    public BlockEnderChest(Identifier identifier, NbtMap blockStates) {
        super(identifier, blockStates);
    }

    @Override
    public boolean placeBlock(Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace) {
        this.setBlockFace(player.getDirection().toBlockFace().opposite());
        BlockEntity.create(BlockEntityType.ENDER_CHEST, this).spawn();

        if (world.getBlock(placePosition) instanceof BlockWater blockWater && blockWater.getLiquidDepth() == 0) {
            world.setBlock(placePosition.add(0, 1, 0), Block.create(BlockType.WATER), 1, false);
        }
        world.setBlock(placePosition, this);
        return true;
    }

    @Override
    public boolean interact(Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand) {
        BlockEntityEnderChest blockEntity = this.getBlockEntity();
        if (blockEntity != null) {
            blockEntity.interact(player, blockPosition, clickedPosition, blockFace, itemInHand);
            return true;
        }
        return false;
    }

    @Override
    public BlockEntityEnderChest getBlockEntity() {
        return (BlockEntityEnderChest) this.location.getWorld().getBlockEntity(this.location, this.location.getDimension());
    }

    @Override
    public int getWaterLoggingLevel() {
        return 1;
    }

    public void setBlockFace(BlockFace blockFace) {
        this.setState("facing_direction", blockFace.ordinal());
    }

    public BlockFace getBlockFace() {
        return this.stateExists("facing_direction") ? BlockFace.values()[this.getIntState("facing_direction")] : BlockFace.NORTH;
    }
}
