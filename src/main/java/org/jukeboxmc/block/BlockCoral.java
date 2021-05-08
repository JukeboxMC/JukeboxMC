package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.CoralColor;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCoral extends Block {

    public BlockCoral() {
        super( "minecraft:coral" );
    }

    @Override
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        int meta = itemIndHand.getMeta();

        if ( meta < 5 ) {
            this.setCoralColor( CoralColor.values()[meta] );
            this.setDead( false );
        } else {
            switch ( meta ) {
                case 8:
                    this.setCoralColor( CoralColor.BLUE );
                    break;
                case 9:
                    this.setCoralColor( CoralColor.PINK );
                    break;
                case 10:
                    this.setCoralColor( CoralColor.PURPLE );
                    break;
                case 11:
                    this.setCoralColor( CoralColor.RED );
                    break;
                case 12:
                    this.setCoralColor( CoralColor.YELLOW );
                    break;
                default:
                    break;
            }
            this.setDead( true );
        }
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public Item toItem() {
        Item item = super.toItem();

        if ( !this.isDead() ) {
            item.setMeta( this.getCoralColor().ordinal() );
        } else {
            switch ( this.getCoralColor() ) {
                case BLUE:
                    item.setMeta( 8 );
                    break;
                case PINK:
                    item.setMeta( 9 );
                    break;
                case PURPLE:
                    item.setMeta( 10 );
                    break;
                case RED:
                    item.setMeta( 11 );
                    break;
                case YELLOW:
                    item.setMeta( 12 );
                    break;
                default:
                    break;
            }
        }
        return item;
    }

    public void setCoralColor( CoralColor coralColor ) {
        this.setState( "coral_color", coralColor.name().toLowerCase() );
    }

    public CoralColor getCoralColor() {
        return this.stateExists( "coral_color" ) ? CoralColor.valueOf( this.getStringState( "coral_color" ).toUpperCase() ) : CoralColor.BLUE;
    }

    public void setDead( boolean value ) {
        this.setState( "dead_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isDead() {
        return this.stateExists( "dead_bit" ) && this.getByteState( "dead_bit" ) == 1;
    }
}
