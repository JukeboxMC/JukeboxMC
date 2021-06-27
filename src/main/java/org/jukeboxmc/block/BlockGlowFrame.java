package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.ItemGlowFrame;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGlowFrame extends Block {

    public BlockGlowFrame() {
        super( "minecraft:glow_frame" );
    }

    @Override
    public ItemGlowFrame toItem() {
        return new ItemGlowFrame();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GLOW_FRAME;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
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
