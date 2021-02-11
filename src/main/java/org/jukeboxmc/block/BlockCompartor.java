package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCompartor extends Block {

    public BlockCompartor() {
        super( "minecraft:unpowered_comparator" );
    }

    @Override
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setDirection( player.getDirection().opposite() );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public boolean interact( Player player, BlockPosition blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        this.setOutputSubstract( !this.isOutputSubstract() );

        this.world.sendBlockUpdate( this );
        this.getChunk().setBlock( this.location, this.layer, this.runtimeId );
        return true;
    }

    public void setOutputSubstract( boolean value ) {
        this.setState( "output_subtract_bit", value ? (byte) 1: (byte) 0 );
    }

    public boolean isOutputSubstract() {
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
