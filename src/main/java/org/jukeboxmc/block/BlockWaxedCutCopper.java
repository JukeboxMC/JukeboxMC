package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWaxedCutCopper;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWaxedCutCopper extends Block{

    public BlockWaxedCutCopper() {
        super( "minecraft:waxed_cut_copper" );
    }

    @Override
    public ItemWaxedCutCopper toItem() {
        return new ItemWaxedCutCopper();
    }

    @Override
    public BlockType getType() {
        return BlockType.WAXED_CUT_COPPER;
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
