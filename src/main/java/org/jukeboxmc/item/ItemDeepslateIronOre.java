package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDeepslateIronOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDeepslateIronOre extends Item{

    public ItemDeepslateIronOre() {
        super( "minecraft:deepslate_iron_ore" );
    }

    @Override
    public BlockDeepslateIronOre getBlock() {
        return new BlockDeepslateIronOre();
    }
}
