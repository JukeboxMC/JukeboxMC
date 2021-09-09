package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDeepslateRedstoneOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslateRedstoneOre extends Block {

    public BlockDeepslateRedstoneOre() {
        super( "minecraft:deepslate_redstone_ore" );
    }

    @Override
    public ItemDeepslateRedstoneOre toItem() {
        return new ItemDeepslateRedstoneOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DEEPSLATE_REDSTONE_ORE;
    }
}
