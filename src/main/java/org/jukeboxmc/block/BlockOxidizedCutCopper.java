package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemOxidizedCutCopper;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockOxidizedCutCopper extends Block{

    public BlockOxidizedCutCopper() {
        super( "minecraft:oxidized_cut_copper" );
    }

    @Override
    public ItemOxidizedCutCopper toItem() {
        return new ItemOxidizedCutCopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.OXIDIZED_CUT_COPPER;
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
