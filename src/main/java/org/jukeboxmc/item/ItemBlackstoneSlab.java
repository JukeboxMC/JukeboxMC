package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBlackstoneSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBlackstoneSlab extends Item {

    public ItemBlackstoneSlab() {
        super( "minecraft:blackstone_slab", -282 );
    }

    @Override
    public Block getBlock() {
        return new BlockBlackstoneSlab();
    }
}
