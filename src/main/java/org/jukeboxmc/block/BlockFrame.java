package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFrame extends Block {

    public BlockFrame() {
        super( "minecraft:frame" );
    }

    public void setItemFrameMap( boolean value ) {
        this.setState( "item_frame_map_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isItemFrameMap() {
        return this.stateExists( "item_frame_map_bit" ) && this.getByteState( "item_frame_map_bit" ) == 1;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
