package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockNetherGoldOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNetherGoldOre extends Item {

    public ItemNetherGoldOre() {
        super( "minecraft:nether_gold_ore", -288 );
    }

    @Override
    public BlockNetherGoldOre getBlock() {
        return new BlockNetherGoldOre();
    }
}
