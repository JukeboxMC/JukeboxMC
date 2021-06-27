package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDeepslateCoalOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDeepslateCoalOre extends Item{

    public ItemDeepslateCoalOre() {
        super( "minecraft:deepslate_coal_ore" );
    }

    @Override
    public BlockDeepslateCoalOre getBlock() {
        return new BlockDeepslateCoalOre();
    }
}
