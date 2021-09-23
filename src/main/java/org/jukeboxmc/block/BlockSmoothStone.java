package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemSmoothStone;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSmoothStone extends Block {

    public BlockSmoothStone() {
        super( "minecraft:smooth_stone" );
    }

    @Override
    public ItemSmoothStone toItem() {
        return new ItemSmoothStone();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SMOOTH_STONE;
    }

    @Override
    public double getHardness() {
        return 2;
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
