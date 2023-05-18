package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCampfire extends Block implements Waterlogable {

    public BlockCampfire( Identifier identifier ) {
        super( identifier );
    }

    public BlockCampfire( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        if (this.getSide(BlockFace.DOWN).getType().equals(BlockType.CAMPFIRE)) {
            return false;
        }
        if (world.getBlock(placePosition) instanceof BlockWater blockWater && blockWater.getLiquidDepth() == 0) {
            this.setExtinguished(true);
            world.setBlock(placePosition, Block.create(BlockType.WATER), 1, false);
        }
        this.setDirection( player.getDirection().opposite() );
        world.setBlock(placePosition, this);
        return true;
    }

    @Override
    public int getWaterLoggingLevel() {
        return 1;
    }

    public void setExtinguished(boolean value ) {
        this.setState( "extinguished", value ? 1 : 0 );
    }

    public boolean isExtinguished() {
        return this.stateExists( "extinguished" ) && this.getIntState( "extinguished" ) == 1;
    }

    public void setDirection( Direction direction ) {
        switch ( direction ) {
            case SOUTH -> this.setState( "direction", 0 );
            case WEST -> this.setState( "direction", 1 );
            case NORTH -> this.setState( "direction", 2 );
            case EAST -> this.setState( "direction", 3 );
        }
    }

    public Direction getDirection() {
        int value = this.stateExists( "direction" ) ? this.getIntState( "direction" ) : 0;
        return switch ( value ) {
            case 0 -> Direction.SOUTH;
            case 1 -> Direction.WEST;
            case 2 -> Direction.NORTH;
            default -> Direction.EAST;
        };
    }
}
