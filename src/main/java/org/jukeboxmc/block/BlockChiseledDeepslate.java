package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemChiseledDeepslate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockChiseledDeepslate extends Block {

    public BlockChiseledDeepslate() {
        super( "minecraft:chiseled_deepslate" );
    }

    @Override
    public ItemChiseledDeepslate toItem() {
        return new ItemChiseledDeepslate();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CHISELED_DEEPSLATE;
    }
}
