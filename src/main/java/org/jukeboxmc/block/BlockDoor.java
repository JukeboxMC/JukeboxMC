package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.LevelEvent;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDoor extends Block {

    public BlockDoor( String identifier ) {
        super( identifier );
    }

    @Override
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setDirection( Direction.fromAngle( player.getYaw() ) );

        BlockDoor blockAbove = new BlockDoor( this.identifier );
        blockAbove.setLocation( new Location( world, placePosition.add( 0, 1, 0 ) ) );
        blockAbove.setDirection( this.getDirection() );
        blockAbove.setUpperBlock( true );
        blockAbove.setOpen( false );

        Block blockLeft = world.getBlock( placePosition ).getSide( player.getDirection().getLeftDirection() );

        if ( blockLeft.getIdentifier().equalsIgnoreCase( this.identifier ) ) {
            blockAbove.setDoorHinge( true );
            this.setDoorHinge( true );
        } else {
            blockAbove.setDoorHinge( false );
            this.setDoorHinge( false );
        }

        this.setUpperBlock( false );
        this.setOpen( false );

        world.setBlock( placePosition.add( 0, 1, 0 ), blockAbove );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public boolean onBlockBreak( BlockPosition breakPosition, boolean isCreative ) {
        if ( this.isUpperBlock() ) {
            this.world.setBlock( this.location.subtract( 0, 1,0 ), new BlockAir() );
        } else {
            this.world.setBlock( this.location.add( 0, 1,0 ), new BlockAir() );
        }
        this.world.setBlock( this.location, new BlockAir() );
        return true;
    }

    @Override
    public boolean interact( Player player, BlockPosition blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        this.setOpen( !this.isOpen() );

        this.world.sendBlockUpdate( this );
        this.getChunk().setBlock( this.location, this.layer, this.runtimeId );
        this.world.sendLevelEvent( this.location, LevelEvent.SOUND_DOOR, 0 );
        return true;
    }

    public void setOpen( boolean value ) {
        this.setState( "open_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isOpen() {
        return this.stateExists( "open_bit" ) && this.getByteState( "open_bit" ) == 1;
    }

    public void setUpperBlock( boolean value ) {
        this.setState( "upper_block_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isUpperBlock() {
        return this.stateExists( "upper_block_bit" ) && this.getByteState( "upper_block_bit" ) == 1;
    }

    public void setDoorHinge( boolean value ) {
        this.setState( "door_hinge_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isDoorHinge() {
        return this.stateExists( "door_hinge_bit" ) && this.getByteState( "door_hinge_bit" ) == 1;
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
