package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockInfestedDeepslate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemInfestedDeepslate extends Item{

    public ItemInfestedDeepslate() {
        super( "minecraft:infested_deepslate" );
    }

    @Override
    public BlockInfestedDeepslate getBlock() {
        return new BlockInfestedDeepslate();
    }
}
