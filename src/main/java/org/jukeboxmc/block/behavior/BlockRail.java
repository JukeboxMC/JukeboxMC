package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.RailDirection;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRail extends Block implements Waterlogable {

    public BlockRail(Identifier identifier) {
        super(identifier);
    }

    public BlockRail(Identifier identifier, NbtMap blockStates) {
        super(identifier, blockStates);
    }

    @Override
    public boolean placeBlock(Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace) {
        Block block = world.getBlock(placePosition);

        if (block instanceof BlockWater blockWater && blockWater.getLiquidDepth() == 0) {
            world.setBlock(placePosition, Block.create(BlockType.WATER), 1,false);
        }

        if (this.getSide(BlockFace.DOWN).isSolid()) {
            world.setBlock(placePosition, this);
            return true;
        }
        return false;
    }

    @Override
    public int getWaterLoggingLevel() {
        return 1;
    }

    public BlockRail setRailDirection(RailDirection railDirection) {
        this.setState("rail_direction", railDirection.ordinal());
        return this;
    }

    public RailDirection getRailDirection() {
        return this.stateExists("rail_direction") ? RailDirection.values()[this.getIntState("rail_direction")] : RailDirection.NORTH_SOUTH;
    }
}
