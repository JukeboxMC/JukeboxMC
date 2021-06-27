package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockTuff;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemTuff extends Item{

    public ItemTuff() {
        super( "minecraft:tuff" );
    }

    @Override
    public BlockTuff getBlock() {
        return new BlockTuff();
    }
}
