package jukeboxmc.block;

import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.item.ItemSmallDripleafBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSmallDripleafBlock extends Block{

    public BlockSmallDripleafBlock() {
        super( "minecraft:small_dripleaf_block" );
    }

    @Override
    public ItemSmallDripleafBlock toItem() {
        return new ItemSmallDripleafBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SMALL_DRIPLEAF_BLOCK;
    }

    public BlockSmallDripleafBlock setUpperBlock( boolean value ) {
        return this.setState( "upper_block_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isUpperBlock() {
        return this.stateExists( "upper_block_bit" ) && this.getByteState( "upper_block_bit" ) == 1;
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
