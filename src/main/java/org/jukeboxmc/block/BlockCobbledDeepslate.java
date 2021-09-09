package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCobbledDeepslate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCobbledDeepslate extends Block {

    public BlockCobbledDeepslate() {
        super( "minecraft:cobbled_deepslate" );
    }

    @Override
    public ItemCobbledDeepslate toItem() {
        return new ItemCobbledDeepslate();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.COBBLED_DEEPSLATE;
    }

}
