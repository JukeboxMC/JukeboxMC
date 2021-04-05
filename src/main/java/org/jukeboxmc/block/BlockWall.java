package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.math.AxisAlignedBB;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWall extends Block {

    public BlockWall( String identifier ) {
        super( identifier );
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public long onUpdate( UpdateReason updateReason ) {
        if ( updateReason == UpdateReason.NEIGHBORS ) {
            this.update();
        }
        return super.onUpdate( updateReason );
    }

    protected void update() {
        for ( Direction value : Direction.values() ) {
            Block block = this.getSide( value );

            if ( this.canConnect( block ) ) {
                this.connect( value, WallConnectionType.SHORT );
            } else {
                this.connect( value, WallConnectionType.NONE );
            }
        }

        if ( this.getWallConnectionType( Direction.NORTH ) == WallConnectionType.SHORT &&
                this.getWallConnectionType( Direction.SOUTH ) == WallConnectionType.SHORT ) {
            this.setWallPost( this.getWallConnectionType( Direction.WEST ) != WallConnectionType.NONE || this.getWallConnectionType( Direction.EAST ) != WallConnectionType.NONE );
        } else if ( this.getWallConnectionType( Direction.WEST ) == WallConnectionType.SHORT && this.getWallConnectionType( Direction.EAST ) == WallConnectionType.SHORT ) {
            this.setWallPost( this.getWallConnectionType( Direction.SOUTH ) != WallConnectionType.NONE ||
                    this.getWallConnectionType( Direction.NORTH ) != WallConnectionType.NONE );
        }

        if ( this.getSide( BlockFace.UP ).isSolid() ) {
            this.setWallPost( true );
        }
        this.getWorld().sendBlockUpdate( this );
        this.getChunk().setBlock( this.location, this.layer, this.runtimeId );
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        boolean north = this.canConnect( this.getSide( BlockFace.NORTH ) );
        boolean south = this.canConnect( this.getSide( BlockFace.SOUTH ) );
        boolean west = this.canConnect( this.getSide( BlockFace.WEST ) );
        boolean east = this.canConnect( this.getSide( BlockFace.EAST ) );

        float n = north ? 0 : 0.25f;
        float s = south ? 1 : 0.75f;
        float w = west ? 0 : 0.25f;
        float e = east ? 1 : 0.75f;

        if ( north && south && !west && !east ) {
            w = 0.3125f;
            e = 0.6875f;
        } else if ( !north && !south && west && east ) {
            n = 0.3125f;
            s = 0.6875f;
        }
        return new AxisAlignedBB(
                this.location.getX() + w,
                this.location.getY(),
                this.location.getZ() + n,
                this.location.getX() + e,
                this.location.getY() + 1.5f,
                this.location.getZ() + s
        );
    }

    public boolean canConnect( Block block ) {
        switch ( block.getBlockType() ) {
            case COBBLESTONE_WALL:
            case BLACKSTONE_WALL:
            case POLISHED_BLACKSTONE_WALL:
            case POLISHED_BLACKSTONE_BRICK_WALL:
            case GLASS_PANE:
            case STAINED_GLASS_PANE:
            case IRON_BARS:
                return true;
            default:
                return block.isSolid() && !block.isTransparent();
        }
    }

    public void connect( Direction direction, WallConnectionType wallConnectionType ) {
        switch ( direction ) {
            case SOUTH:
                this.setWallConnectionTypeSouth( wallConnectionType );
                break;
            case EAST:
                this.setWallConnectionTypeEast( wallConnectionType );
                break;
            case WEST:
                this.setWallConnectionTypeWest( wallConnectionType );
                break;
            case NORTH:
                this.setWallConnectionTypeNorth( wallConnectionType );
                break;
        }
    }

    public WallConnectionType getWallConnectionType( Direction direction ) {
        switch ( direction ) {
            case SOUTH:
                return this.getWallConnectionTypeSouth();
            case EAST:
                return this.getWallConnectionTypeEast();
            case WEST:
                return this.getWallConnectionTypeWest();
            case NORTH:
                return this.getWallConnectionTypeNorth();
        }
        return null;
    }

    public void setWallPost( boolean value ) {
        this.setState( "wall_post_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isWallPost() {
        return this.stateExists( "wall_post_bit" ) && this.getByteState( "wall_post_bit" ) == 1;
    }

    public void setWallConnectionTypeEast( WallConnectionType wallConnectionTypeEast ) {
        this.setState( "wall_connection_type_east", wallConnectionTypeEast.name().toLowerCase() );
    }

    public WallConnectionType getWallConnectionTypeEast() {
        return this.stateExists( "wall_connection_type_east" ) ? WallConnectionType.valueOf( this.getStringState( "wall_connection_type_east" ).toUpperCase() ) : WallConnectionType.NONE;
    }

    public void setWallConnectionTypeSouth( WallConnectionType wallConnectionTypeEast ) {
        this.setState( "wall_connection_type_south", wallConnectionTypeEast.name().toLowerCase() );
    }

    public WallConnectionType getWallConnectionTypeSouth() {
        return this.stateExists( "wall_connection_type_south" ) ? WallConnectionType.valueOf( this.getStringState( "wall_connection_type_south" ).toUpperCase() ) : WallConnectionType.NONE;
    }

    public void setWallConnectionTypeWest( WallConnectionType wallConnectionTypeEast ) {
        this.setState( "wall_connection_type_west", wallConnectionTypeEast.name().toLowerCase() );
    }

    public WallConnectionType getWallConnectionTypeWest() {
        return this.stateExists( "wall_connection_type_west" ) ? WallConnectionType.valueOf( this.getStringState( "wall_connection_type_west" ).toUpperCase() ) : WallConnectionType.NONE;
    }

    public void setWallConnectionTypeNorth( WallConnectionType wallConnectionTypeEast ) {
        this.setState( "wall_connection_type_north", wallConnectionTypeEast.name().toLowerCase() );
    }

    public WallConnectionType getWallConnectionTypeNorth() {
        return this.stateExists( "wall_connection_type_north" ) ? WallConnectionType.valueOf( this.getStringState( "wall_connection_type_north" ).toUpperCase() ) : WallConnectionType.NONE;
    }
}
