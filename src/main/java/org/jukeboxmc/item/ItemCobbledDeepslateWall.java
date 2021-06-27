package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCobbledDeepslateWall;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCobbledDeepslateWall extends Item{

    public ItemCobbledDeepslateWall() {
        super( "minecraft:cobbled_deepslate_wall" );
    }

    @Override
    public BlockCobbledDeepslateWall getBlock() {
        return new BlockCobbledDeepslateWall();
    }
}
