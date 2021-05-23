package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemObsidian;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGlowingObsidian extends Block {

    public BlockGlowingObsidian() {
        super( "minecraft:glowingobsidian" );
    }

    @Override
    public ItemObsidian toItem() {
        return new ItemObsidian();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GLOWING_OBSIDIAN;
    }

}
