package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWeatheredCutCopper;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWeatheredCutCopper extends Block{

    public BlockWeatheredCutCopper() {
        super( "minecraft:weathered_cut_copper" );
    }

    @Override
    public ItemWeatheredCutCopper toItem() {
        return new ItemWeatheredCutCopper();
    }

    @Override
    public BlockType getType() {
        return BlockType.WEATHERED_CUT_COPPER;
    }

    @Override
    public double getHardness() {
        return 3;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public ItemTierType getTierType() {
        return ItemTierType.STONE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }
}
