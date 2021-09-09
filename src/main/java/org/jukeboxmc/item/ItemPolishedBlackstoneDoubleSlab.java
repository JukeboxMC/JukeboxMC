package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDoublePolishedBlackstoneSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedBlackstoneDoubleSlab extends Item {

    public ItemPolishedBlackstoneDoubleSlab() {
        super ( "minecraft:polished_blackstone_double_slab" );
    }

    @Override
    public BlockDoublePolishedBlackstoneSlab getBlock() {
        return new BlockDoublePolishedBlackstoneSlab();
    }
}
