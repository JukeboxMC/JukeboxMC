package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPolishedBlackstoneBricks;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedBlackstoneBricks extends Item {

    public ItemPolishedBlackstoneBricks() {
        super ( "minecraft:polished_blackstone_bricks" );
    }

    @Override
    public BlockPolishedBlackstoneBricks getBlock() {
        return new BlockPolishedBlackstoneBricks();
    }
}
