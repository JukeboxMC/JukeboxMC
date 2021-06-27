package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDeepslateEmeraldOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDeepslateEmeraldOre extends Item{

    public ItemDeepslateEmeraldOre() {
        super( "minecraft:deepslate_emerald_ore" );
    }

    @Override
    public BlockDeepslateEmeraldOre getBlock() {
        return new BlockDeepslateEmeraldOre();
    }
}
