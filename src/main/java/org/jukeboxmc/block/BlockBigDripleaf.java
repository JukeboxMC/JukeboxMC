package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.block.type.BigDripleafTilt;
import org.jukeboxmc.item.ItemBigDripleaf;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBigDripleaf extends Block{

    public BlockBigDripleaf() {
        super( "minecraft:big_dripleaf" );
    }

    @Override
    public ItemBigDripleaf toItem() {
        return new ItemBigDripleaf();
    }

    @Override
    public BlockType getType() {
        return BlockType.BIG_DRIPLEAF;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public double getHardness() {
        return 0.1;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.AXE;
    }

    public void setBigDripleafTilt( BigDripleafTilt bigDripleafTilt ) {
        this.setState( "big_dripleaf_tilt", bigDripleafTilt.name().toLowerCase() );
    }

    public BigDripleafTilt getBigDripleafTilt() {
        return this.stateExists( "big_dripleaf_tilt" ) ? BigDripleafTilt.valueOf( this.getStringState( "big_dripleaf_tilt" ) ) : BigDripleafTilt.NONE;
    }

    public void setBigDripleafHead( boolean value ) {
        this.setState( "big_dripleaf_head", value );
    }

    public boolean isBigDripleafHead() {
        return this.stateExists( "big_dripleaf_head" ) && this.getByteState( "big_dripleaf_head" ) == 1;
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
