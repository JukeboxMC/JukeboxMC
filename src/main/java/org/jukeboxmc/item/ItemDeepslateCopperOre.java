package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDeepslateCopperOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDeepslateCopperOre extends Item{

    public ItemDeepslateCopperOre() {
        super( "minecraft:deepslate_copper_ore" );
    }

    @Override
    public BlockDeepslateCopperOre getBlock() {
        return new BlockDeepslateCopperOre();
    }
}
