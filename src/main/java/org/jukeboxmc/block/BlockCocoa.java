package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.item.ItemCocoa;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCocoa extends Block {

    public BlockCocoa() {
        super( "minecraft:cocoa" );
    }

    @Override
    public ItemCocoa toItem() {
        return new ItemCocoa();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.COCOA;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    public void setAge( int value ) { // 0-15
        this.setState( "age", value );
    }

    public int getAge() {
        return this.stateExists( "age" ) ? this.getIntState( "age" ) : 0;
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
