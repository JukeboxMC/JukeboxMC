package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWaxedExposedCutCopper;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWaxedExposedCutCopper extends Block{

    public BlockWaxedExposedCutCopper() {
        super( "minecraft:waxed_exposed_cut_copper" );
    }

    @Override
    public ItemWaxedExposedCutCopper toItem() {
        return new ItemWaxedExposedCutCopper();
    }

    @Override
    public BlockType getType() {
        return BlockType.WAXED_EXPOSED_CUT_COPPER;
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
