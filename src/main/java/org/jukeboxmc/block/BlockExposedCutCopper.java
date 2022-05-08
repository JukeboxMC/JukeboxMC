package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemExposedCutCopper;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockExposedCutCopper extends Block{

    public BlockExposedCutCopper() {
        super( "minecraft:exposed_cut_copper" );
    }

    @Override
    public ItemExposedCutCopper toItem() {
        return new ItemExposedCutCopper();
    }

    @Override
    public BlockType getType() {
        return BlockType.EXPOSED_CUT_COPPER;
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
