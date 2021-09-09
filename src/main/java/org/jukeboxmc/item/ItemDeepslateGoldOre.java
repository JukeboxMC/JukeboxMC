package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDeepslateGoldOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDeepslateGoldOre extends Item{

    public ItemDeepslateGoldOre() {
        super( "minecraft:deepslate_gold_ore" );
    }

    @Override
    public BlockDeepslateGoldOre getBlock() {
        return new BlockDeepslateGoldOre();
    }
}
