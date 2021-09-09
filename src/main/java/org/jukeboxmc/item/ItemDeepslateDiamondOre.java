package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDeepslateDiamondOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDeepslateDiamondOre extends Item{

    public ItemDeepslateDiamondOre() {
        super( "minecraft:deepslate_diamond_ore" );
    }

    @Override
    public BlockDeepslateDiamondOre getBlock() {
        return new BlockDeepslateDiamondOre();
    }
}
