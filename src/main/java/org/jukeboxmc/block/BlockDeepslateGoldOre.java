package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDeepslateGoldOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslateGoldOre extends Block{

    public BlockDeepslateGoldOre() {
        super( "minecraft:deepslate_gold_ore" );
    }

    @Override
    public ItemDeepslateGoldOre toItem() {
        return new ItemDeepslateGoldOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DEEPSLATE_GOLD_ORE;
    }
}
