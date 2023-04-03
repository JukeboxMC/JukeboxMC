package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.UpdateReason;
import org.jukeboxmc.block.data.WallConnectionType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWall extends Block {

    public BlockWall( Identifier identifier ) {
        super( identifier );
    }

    public BlockWall( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        this.updateWall();
        return super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace );
    }

    @Override
    public long onUpdate( UpdateReason updateReason ) {
        if ( updateReason == UpdateReason.NEIGHBORS ) {
            this.updateWall();
        }
        return super.onUpdate( updateReason );
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

    protected void updateWall() {
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
        this.sendUpdate();
        this.location.getChunk().setBlock( this.location.getBlockX(), this.location.getBlockY(), this.location.getBlockZ(), 0, this );
    }

    public boolean canConnect( Block block ) {
        return switch ( block.getType() ) {
            case COBBLESTONE_WALL,
                    BLACKSTONE_WALL,
                    POLISHED_BLACKSTONE_WALL,
                    POLISHED_BLACKSTONE_BRICK_WALL,
                    POLISHED_DEEPSLATE_WALL,
                    DEEPSLATE_BRICK_WALL,
                    DEEPSLATE_TILE_WALL,
                    MUD_BRICK_WALL,
                    COBBLED_DEEPSLATE_WALL,
                    GLASS_PANE,
                    STAINED_GLASS_PANE,
                    IRON_BARS ->
                    true;
            default -> block.isSolid() && !block.isTransparent();
        };
    }

    public void connect( Direction direction, WallConnectionType wallConnectionType ) {
        switch ( direction ) {
            case SOUTH -> this.setWallConnectionTypeSouth( wallConnectionType );
            case EAST -> this.setWallConnectionTypeEast( wallConnectionType );
            case WEST -> this.setWallConnectionTypeWest( wallConnectionType );
            case NORTH -> this.setWallConnectionTypeNorth( wallConnectionType );
        }
    }

    public WallConnectionType getWallConnectionType( Direction direction ) {
        return switch ( direction ) {
            case SOUTH -> this.getWallConnectionTypeSouth();
            case EAST -> this.getWallConnectionTypeEast();
            case WEST -> this.getWallConnectionTypeWest();
            case NORTH -> this.getWallConnectionTypeNorth();
        };
    }

    public void setWallPost( boolean value ) {
        this.setState( "wall_post_bit", value ? 1 : 0 );
    }

    public boolean isWallPost() {
        return this.stateExists( "wall_post_bit" ) && this.getByteState( "wall_post_bit" ) == 1;
    }

    public void setWallConnectionTypeEast( WallConnectionType wallConnectionTypeEast ) {
        this.setState( "wall_connection_type_east", wallConnectionTypeEast.name().toLowerCase() );
    }

    public WallConnectionType getWallConnectionTypeEast() {
        return this.stateExists( "wall_connection_type_east" ) ? WallConnectionType.valueOf( this.getStringState( "wall_connection_type_east" ) ) : WallConnectionType.NONE;
    }

    public void setWallConnectionTypeSouth( WallConnectionType wallConnectionTypeEast ) {
        this.setState( "wall_connection_type_south", wallConnectionTypeEast.name().toLowerCase() );
    }

    public WallConnectionType getWallConnectionTypeSouth() {
        return this.stateExists( "wall_connection_type_south" ) ? WallConnectionType.valueOf( this.getStringState( "wall_connection_type_south" ) ) : WallConnectionType.NONE;
    }

    public void setWallConnectionTypeWest( WallConnectionType wallConnectionTypeEast ) {
        this.setState( "wall_connection_type_west", wallConnectionTypeEast.name().toLowerCase() );
    }

    public WallConnectionType getWallConnectionTypeWest() {
        return this.stateExists( "wall_connection_type_west" ) ? WallConnectionType.valueOf( this.getStringState( "wall_connection_type_west" ) ) : WallConnectionType.NONE;
    }

    public void setWallConnectionTypeNorth( WallConnectionType wallConnectionTypeEast ) {
        this.setState( "wall_connection_type_north", wallConnectionTypeEast.name().toLowerCase() );
    }

    public WallConnectionType getWallConnectionTypeNorth() {
        return this.stateExists( "wall_connection_type_north" ) ? WallConnectionType.valueOf( this.getStringState( "wall_connection_type_north" ) ) : WallConnectionType.NONE;
    }
}
