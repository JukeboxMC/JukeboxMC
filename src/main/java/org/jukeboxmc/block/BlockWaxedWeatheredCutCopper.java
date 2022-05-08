package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWaxedWeatheredCutCopper;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWaxedWeatheredCutCopper extends Block{

    public BlockWaxedWeatheredCutCopper() {
        super( "minecraft:waxed_weathered_cut_copper" );
    }

    @Override
    public ItemWaxedWeatheredCutCopper toItem() {
        return new ItemWaxedWeatheredCutCopper();
    }

    @Override
    public BlockType getType() {
        return BlockType.WAXED_WEATHERED_CUT_COPPER;
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
