package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPolishedBlackstoneSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedBlackstoneSlab extends Item {

    public ItemPolishedBlackstoneSlab() {
        super( "minecraft:polished_blackstone_slab", -293 );
    }

    @Override
    public BlockPolishedBlackstoneSlab getBlock() {
        return new BlockPolishedBlackstoneSlab();
    }
}
