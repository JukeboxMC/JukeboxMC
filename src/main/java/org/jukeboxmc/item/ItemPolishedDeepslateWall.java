package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPolishedDeepslateWall;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedDeepslateWall extends Item{

    public ItemPolishedDeepslateWall() {
        super( "minecraft:polished_deepslate_wall" );
    }

    @Override
    public BlockPolishedDeepslateWall getBlock() {
        return new BlockPolishedDeepslateWall();
    }
}
