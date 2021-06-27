package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemInfestedDeepslate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockInfestedDeepslate extends Block {

    public BlockInfestedDeepslate() {
        super( "minecraft:infested_deepslate" );
    }

    @Override
    public ItemInfestedDeepslate toItem() {
        return new ItemInfestedDeepslate();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.INFESTED_DEEPSLATE;
    }
}
