package org.jukeboxmc.block;

import org.jukeboxmc.block.type.WallConnectionType;
import org.jukeboxmc.item.ItemBorderBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBorderBlock extends Block {

    public BlockBorderBlock() {
        super( "minecraft:border_block" );
    }

    @Override
    public ItemBorderBlock toItem() {
        return new ItemBorderBlock();
    }

    @Override
    public BlockType getType() {
        return BlockType.BORDER_BLOCK;
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
