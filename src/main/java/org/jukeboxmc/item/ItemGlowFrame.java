package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockGlowFrame;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGlowFrame extends Item{

    public ItemGlowFrame() {
        super( "minecraft:glow_frame" );
    }

    @Override
    public BlockGlowFrame getBlock() {
        return new BlockGlowFrame();
    }
}
