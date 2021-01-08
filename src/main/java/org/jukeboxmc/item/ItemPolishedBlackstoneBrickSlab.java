package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPolishedBlackstoneBrickSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedBlackstoneBrickSlab extends Item {

    public ItemPolishedBlackstoneBrickSlab() {
        super( "minecraft:polished_blackstone_brick_slab", -284 );
    }

    @Override
    public BlockPolishedBlackstoneBrickSlab getBlock() {
        return new BlockPolishedBlackstoneBrickSlab();
    }
}
