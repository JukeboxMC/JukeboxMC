package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDirtWithRoots;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDirtWithRoots extends Item{

    public ItemDirtWithRoots() {
        super( "minecraft:dirt_with_roots" );
    }

    @Override
    public BlockDirtWithRoots getBlock() {
        return new BlockDirtWithRoots();
    }
}
