package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemIronBars;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockIronBars extends BlockWaterlogable {

    public BlockIronBars() {
        super( "minecraft:iron_bars" );
    }

    @Override
    public ItemIronBars toItem() {
        return new ItemIronBars();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.IRON_BARS;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public double getHardness() {
        return 5;
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
