package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemDeepslate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslate extends Block {

    public BlockDeepslate() {
        super( "minecraft:deepslate" );
    }

    @Override
    public ItemDeepslate toItem() {
        return new ItemDeepslate();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DEEPSLATE;
    }
}
