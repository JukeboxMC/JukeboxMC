package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.block.type.Attachment;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemBell;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBell extends BlockWaterlogable {

    public BlockBell() {
        super( "minecraft:bell" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
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
        return super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
    }

    @Override
    public ItemBell toItem() {
        return new ItemBell();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BELL;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
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
