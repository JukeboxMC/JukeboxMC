package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.Direction;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGrindstone extends Block {

    public BlockGrindstone() {
        super( "minecraft:grindstone" );
    }

    public void setAttachment( Attachment attachment ) {
        this.setState( "attachment", attachment.name().toLowerCase() );
    }

    public Attachment getAttachment() {
        return this.stateExists( "attachment" ) ? Attachment.valueOf( this.getStringState( "attachment" ).toUpperCase() ) : Attachment.STANDING;
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

    public enum Attachment {
        STANDING,
        HANGING,
        SIDE,
        MULTIPLE
    }
}
