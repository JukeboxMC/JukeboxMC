package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPolishedBlackstoneBrickWall;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedBlackstoneBrickWall extends Item {

    public ItemPolishedBlackstoneBrickWall() {
        super ( "minecraft:polished_blackstone_brick_wall" );
    }

    @Override
    public BlockPolishedBlackstoneBrickWall getBlock() {
        return new BlockPolishedBlackstoneBrickWall();
    }
}
