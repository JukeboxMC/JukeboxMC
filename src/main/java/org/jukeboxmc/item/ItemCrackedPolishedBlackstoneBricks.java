package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCrackedPolishedBlackstoneBricks;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrackedPolishedBlackstoneBricks extends Item {

    public ItemCrackedPolishedBlackstoneBricks() {
        super( "minecraft:cracked_polished_blackstone_bricks", -280 );
    }

    @Override
    public Block getBlock() {
        return new BlockCrackedPolishedBlackstoneBricks();
    }
}
