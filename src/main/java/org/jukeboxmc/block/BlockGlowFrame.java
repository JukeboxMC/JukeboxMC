package org.jukeboxmc.block;

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
}
