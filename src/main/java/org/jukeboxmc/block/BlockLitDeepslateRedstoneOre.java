package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemLitDeepslateRedstoneOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLitDeepslateRedstoneOre extends Block{

    public BlockLitDeepslateRedstoneOre() {
        super( "minecraft:lit_deepslate_redstone_ore" );
    }

    @Override
    public ItemLitDeepslateRedstoneOre toItem() {
        return new ItemLitDeepslateRedstoneOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LIT_DEEPSLATE_REDSTONE_ORE;
    }
}
