package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemComparator;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCompartor extends BlockWaterlogable {

    public BlockCompartor() {
        super( "minecraft:unpowered_comparator" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setDirection( player.getDirection().opposite() );
        return super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        this.setOutputSubtract( !this.isOutputSubtract() );
        return true;
    }

    @Override
    public ItemComparator toItem() {
        return new ItemComparator();
    }

    @Override
    public BlockType getType() {
        return BlockType.UNPOWERED_COMPARATOR;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    public void setOutputSubtract( boolean value ) {
        this.setState( "output_subtract_bit", value ? (byte) 1: (byte) 0 );
    }

    public boolean isOutputSubtract() {
        return this.stateExists( "output_subtract_bit" ) && this.getByteState( "output_subtract_bit" ) == 1;
    }

    public void setOutputLit( boolean value ) {
        this.setState( "output_lit_bit", value ? (byte) 1: (byte) 0 );
    }

    public boolean isOutputLit() {
        return this.stateExists( "output_lit_bit" ) && this.getByteState( "output_lit_bit" ) == 1;
    }

    public void setDirection( Direction direction ) {
        switch ( direction ) {
            case SOUTH:
                this.setState( "direction", 0 );
                break;
            case WEST:
                this.setState( "direction", 1 );
                break;
            case NORTH:
                this.setState( "direction", 2 );
                break;
            case EAST:
                this.setState( "direction", 3 );
                break;
        }
    }

    public Direction getDirection() {
        int value = this.stateExists( "direction" ) ? this.getIntState( "direction" ) : 0;
        switch ( value ) {
            case 0:
                return Direction.SOUTH;
            case 1:
                return Direction.WEST;
            case 2:
                return Direction.NORTH;
            default:
                return Direction.EAST;
        }
    }
}
