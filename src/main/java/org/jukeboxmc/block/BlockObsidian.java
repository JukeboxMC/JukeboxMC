package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemObsidian;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockObsidian extends Block {

    public BlockObsidian() {
        super( "minecraft:obsidian" );
    }

    @Override
    public ItemObsidian toItem() {
        return new ItemObsidian();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.OBSIDIAN;
    }

}
