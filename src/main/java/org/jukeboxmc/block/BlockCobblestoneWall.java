package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
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
    public void placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setWallBlockType( WallType.values()[itemIndHand.getMeta()] );
        world.setBlock( placePosition, this );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getWallConnectionType().ordinal() );
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

    public void setWallBlockType( WallType wallType ) {
        this.setState( "wall_block_type", wallType.name().toLowerCase() );
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
