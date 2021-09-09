package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemPolishedDeepslate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPolishedDeepslate extends Block{

    public BlockPolishedDeepslate() {
        super( "minecraft:polished_deepslate" );
    }

    @Override
    public ItemPolishedDeepslate toItem() {
        return new ItemPolishedDeepslate();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.POLISHED_DEEPSLATE;
    }
}
