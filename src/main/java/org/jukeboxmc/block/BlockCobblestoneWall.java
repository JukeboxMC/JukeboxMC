package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCobblestoneWall extends Block {

    public BlockCobblestoneWall() {
        super( "minecraft:cobblestone_wall" );
    }

    @Override
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setWallBlockType( WallType.values()[itemIndHand.getMeta()] );
        this.update();
        world.setBlock( placePosition, this );
        return true;
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

    private void update() {
        // Check if we have others around and update connections if needed
        for ( Direction value : Direction.values() ) {
            Block block = this.getSide( value );

            if ( this.canConnect( block ) ) {
                this.connect( value, WallConnectionType.SHORT );
            } else {
                this.connect( value, WallConnectionType.NONE );
            }
        }

        // Check if we need the pole
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
    }

    public boolean canConnect(Block block) {
        switch (block.getBlockType()) {
            case COBBLESTONE_WALL:
            case GLASS_PANE:
            case STAINED_GLASS_PANE:
            case IRON_BARS:
                return true;
            default:
                //TODO: Something with fences
                /*if (block instanceof BlockFenceGate) {
                    BlockFenceGate fenceGate = (BlockFenceGate) block;
                    return fenceGate.getBlockFace().getAxis() != calculateAxis(block);
                } else if (block instanceof BlockStairs) {
                    return ((BlockStairs) block).getBlockFace() == calculateFace(block);
                }*/
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

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getWallConnectionType().ordinal() );
    }

    public void setWallPost( boolean value ) {
        this.setState( "wall_post_bit", value ? (byte) 1 : (byte) 0 );
        this.getWorld().sendBlockUpdate( this );
        this.getChunk().setBlock( this.location, this.layer, this.runtimeId );
    }

    public boolean isWallPost() {
        return this.stateExists( "wall_post_bit" ) && this.getByteState( "wall_post_bit" ) == 1;
    }

    public void setWallConnectionTypeEast( WallConnectionType wallConnectionTypeEast ) {
        this.setState( "wall_connection_type_east", wallConnectionTypeEast.name().toLowerCase() );
        this.getWorld().sendBlockUpdate( this );
        this.getChunk().setBlock( this.location, this.layer, this.runtimeId );
    }

    public WallConnectionType getWallConnectionTypeEast() {
        return this.stateExists( "wall_connection_type_east" ) ? WallConnectionType.valueOf( this.getStringState( "wall_connection_type_east" ).toUpperCase() ) : WallConnectionType.NONE;
    }

    public void setWallConnectionTypeSouth( WallConnectionType wallConnectionTypeEast ) {
        this.setState( "wall_connection_type_south", wallConnectionTypeEast.name().toLowerCase() );
        this.getWorld().sendBlockUpdate( this );
        this.getChunk().setBlock( this.location, this.layer, this.runtimeId );
    }

    public WallConnectionType getWallConnectionTypeSouth() {
        return this.stateExists( "wall_connection_type_south" ) ? WallConnectionType.valueOf( this.getStringState( "wall_connection_type_south" ).toUpperCase() ) : WallConnectionType.NONE;
    }

    public void setWallConnectionTypeWest( WallConnectionType wallConnectionTypeEast ) {
        this.setState( "wall_connection_type_west", wallConnectionTypeEast.name().toLowerCase() );
        this.getWorld().sendBlockUpdate( this );
        this.getChunk().setBlock( this.location, this.layer, this.runtimeId );
    }

    public WallConnectionType getWallConnectionTypeWest() {
        return this.stateExists( "wall_connection_type_west" ) ? WallConnectionType.valueOf( this.getStringState( "wall_connection_type_west" ).toUpperCase() ) : WallConnectionType.NONE;
    }

    public void setWallConnectionTypeNorth( WallConnectionType wallConnectionTypeEast ) {
        this.setState( "wall_connection_type_north", wallConnectionTypeEast.name().toLowerCase() );
        this.getWorld().sendBlockUpdate( this );
        this.getChunk().setBlock( this.location, this.layer, this.runtimeId );
    }

    public WallConnectionType getWallConnectionTypeNorth() {
        return this.stateExists( "wall_connection_type_north" ) ? WallConnectionType.valueOf( this.getStringState( "wall_connection_type_north" ).toUpperCase() ) : WallConnectionType.NONE;
    }

    public void setWallBlockType( WallType wallType ) {
        this.setState( "wall_block_type", wallType.name().toLowerCase() );
        this.getWorld().sendBlockUpdate( this );
        this.getChunk().setBlock( this.location, this.layer, this.runtimeId );
    }

    public WallType getWallConnectionType() {
        return this.stateExists( "wall_block_type" ) ? WallType.valueOf( this.getStringState( "wall_block_type" ).toUpperCase() ) : WallType.COBBLESTONE;
    }

    public enum WallType {
        COBBLESTONE,
        MOSSY_COBBLESTONE,
        GRANITE,
        DIORITE,
        ANDESITE,
        SANDSTONE,
        BRICK,
        STONE_BRICK,
        MOSSY_STONE_BRICK,
        NETHER_BRICK,
        END_BRICK,
        PRISMARINE,
        RED_SANDSTONE,
        RED_NETHER_BRICK
    }
}
