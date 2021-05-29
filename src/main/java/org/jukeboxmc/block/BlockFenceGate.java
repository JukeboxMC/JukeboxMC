package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.LevelEvent;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class BlockFenceGate extends Block {

    public BlockFenceGate( String identifier ) {
        super( identifier );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setDirection( player.getDirection() );
        this.setOpen( false );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        Direction playerDirection = player.getDirection();
        Direction direction = this.getDirection();

        if ( playerDirection == Direction.NORTH ) {
            if ( direction == Direction.NORTH || direction == Direction.SOUTH ) {
                this.setDirection( Direction.NORTH );
            }
        } else if ( playerDirection == Direction.EAST ) {
            if ( direction == Direction.EAST || direction == Direction.WEST ) {
                this.setDirection( Direction.EAST );
            }
        } else if ( playerDirection == Direction.SOUTH ) {
            if ( direction == Direction.NORTH || direction == Direction.SOUTH ) {
                this.setDirection( Direction.SOUTH );
            }
        } else if ( playerDirection == Direction.WEST ) {
            if ( direction == Direction.EAST || direction == Direction.WEST ) {
                this.setDirection( Direction.WEST );
            }
        }
        this.setOpen( !this.isOpen() );

        this.world.sendLevelEvent( this.location, LevelEvent.SOUND_DOOR_OPEN, 0 );
        return true;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    private int getIndex( int x, int y, int z ) {
        return ( x << 8 ) + ( z << 4 ) + y;
    }

    public void setInWall( boolean value ) {
        this.setState( "in_wall_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isInWall() {
        return this.stateExists( "in_wall_bit" ) && this.getByteState( "in_wall_bit" ) == 1;
    }

    public void setOpen( boolean value ) {
        this.setState( "open_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isOpen() {
        return this.stateExists( "open_bit" ) && this.getByteState( "open_bit" ) == 1;
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
