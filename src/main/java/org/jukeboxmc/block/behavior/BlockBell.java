package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.Attachment;
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
public class BlockBell extends Block {

    public BlockBell( Identifier identifier ) {
        super( identifier );
    }

    public BlockBell( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        this.setDirection( player.getDirection().opposite() );
        if ( blockFace == BlockFace.UP ) {
            this.setAttachment( Attachment.STANDING );
        } else if ( blockFace == BlockFace.DOWN ) {
            this.setAttachment( Attachment.HANGING );
        } else {
            this.setDirection( blockFace.toDirection() );
            if( world.getBlock( placePosition ).getSide( blockFace ).isSolid() && world.getBlock( placePosition ).getSide( blockFace.opposite() ).isSolid() ) {
                this.setAttachment( Attachment.MULTIPLE );
            } else {
                if ( world.getBlock( blockPosition ).isSolid() ) {
                    this.setAttachment( Attachment.SIDE );
                } else {
                    return false;
                }
            }
        }
        return super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace );
    }

    public void setAttachment( Attachment attachment ) {
        this.setState( "attachment", attachment.name().toLowerCase() );
    }

    public Attachment getAttachment() {
        return this.stateExists( "attachment" ) ? Attachment.valueOf( this.getStringState( "attachment" ) ) : Attachment.STANDING;
    }

    public void setToggle( boolean value ) {
        this.setState( "toggle_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isToggle() {
        return this.stateExists( "toggle_bit" ) && this.getByteState( "toggle_bit" ) == 1;
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
