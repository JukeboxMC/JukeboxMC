package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCalcite;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCalcite extends Block{

    public BlockCalcite() {
        super( "minecraft:calcite" );
    }

    @Override
    public ItemCalcite toItem() {
        return new ItemCalcite();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CALCITE;
    }

    @Override
    public double getHardness() {
        return 0.75;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }
}
