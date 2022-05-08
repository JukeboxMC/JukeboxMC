package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDeepslateGoldOre;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

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
    public BlockType getType() {
        return BlockType.DEEPSLATE_GOLD_ORE;
    }

    @Override
    public double getHardness() {
        return 4.5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public ItemTierType getTierType() {
        return ItemTierType.IRON;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }
}
