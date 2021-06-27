package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLitDeepslateRedstoneOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLitDeepslateRedstoneOre extends Item {

    public ItemLitDeepslateRedstoneOre() {
        super( "minecraft:lit_deepslate_redstone_ore" );
    }

    @Override
    public BlockLitDeepslateRedstoneOre getBlock() {
        return new BlockLitDeepslateRedstoneOre();
    }
}
