package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDeepslateRedstoneOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDeepslateRedstoneOre extends Item {

    public ItemDeepslateRedstoneOre() {
        super( "minecraft:deepslate_redstone_ore" );
    }

    @Override
    public BlockDeepslateRedstoneOre getBlock() {
        return new BlockDeepslateRedstoneOre();
    }
}
