package jukeboxmc.block;

import org.jukeboxmc.block.direction.RailDirection;
import org.jukeboxmc.item.ItemDetectorRail;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDetectorRail extends BlockWaterlogable {

    public BlockDetectorRail() {
        super( "minecraft:detector_rail" );
    }

    @Override
    public ItemDetectorRail toItem() {
        return new ItemDetectorRail();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DETECTOR_RAIL;
    }

    public void setRailData( boolean value ) {
        this.setState( "rail_data_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isRailData() {
        return this.stateExists( "rail_data_bit" ) && this.getByteState( "rail_data_bit" ) == 1;
    }

    public void setRailDirection( RailDirection railDirection ) {
        switch ( railDirection ) {
            case NORTH_SOUTH:
                this.setState( "rail_direction", 0 );
                break;
            case EAST_WEST:
                this.setState( "rail_direction", 1 );
                break;
            case ASCENDING_EAST:
                this.setState( "rail_direction", 2 );
                break;
            case ASCENDING_WEST:
                this.setState( "rail_direction", 3 );
                break;
            case ASCENDING_NORTH:
                this.setState( "rail_direction", 4 );
                break;
            case ASCENDING_SOUTH:
                this.setState( "rail_direction", 5 );
                break;
            case SOUTH_EAST:
                this.setState( "rail_direction", 6 );
                break;
            case SOUTH_WEST:
                this.setState( "rail_direction", 7 );
                break;
            case NORTH_WEST:
                this.setState( "rail_direction", 8 );
                break;
            case NORTH_EAST:
                this.setState( "rail_direction", 9 );
            default:
                break;
        }
    }

    public RailDirection getRailDirection() {
        int value = this.stateExists( "rail_direction" ) ? this.getIntState( "rail_direction" ) : 0;
        switch ( value ) {
            case 0:
                return RailDirection.NORTH_SOUTH;
            case 1:
                return RailDirection.EAST_WEST;
            case 2:
                return RailDirection.ASCENDING_EAST;
            case 3:
                return RailDirection.ASCENDING_WEST;
            case 4:
                return RailDirection.ASCENDING_NORTH;
            case 5:
                return RailDirection.ASCENDING_SOUTH;
            case 6:
                return RailDirection.SOUTH_EAST;
            case 7:
                return RailDirection.SOUTH_WEST;
            case 8:
                return RailDirection.NORTH_WEST;
            default:
                return RailDirection.NORTH_EAST;
        }
    }
}
