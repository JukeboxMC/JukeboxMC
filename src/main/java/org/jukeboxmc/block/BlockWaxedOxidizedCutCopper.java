package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemTierType;
import org.jukeboxmc.item.ItemToolType;
import org.jukeboxmc.item.ItemWaxedOxidizedCutCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWaxedOxidizedCutCopper extends Block{

    public BlockWaxedOxidizedCutCopper() {
        super( "minecraft:waxed_oxidized_cut_copper" );
    }

    @Override
    public ItemWaxedOxidizedCutCopper toItem() {
        return new ItemWaxedOxidizedCutCopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WAXED_OXIDIZED_CUT_COPPER;
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
