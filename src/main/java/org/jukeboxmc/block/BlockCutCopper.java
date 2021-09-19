package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCutCopper;
import org.jukeboxmc.item.ItemTierType;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCutCopper extends Block {

    public BlockCutCopper() {
        super( "minecraft:cut_copper" );
    }

    @Override
    public ItemCutCopper toItem() {
        return new ItemCutCopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CUT_COPPER;
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
