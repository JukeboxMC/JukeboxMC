package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemEndBricks;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEndBricks extends Block {

    public BlockEndBricks() {
        super( "minecraft:end_bricks" );
    }

    @Override
    public ItemEndBricks toItem() {
        return new ItemEndBricks();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.END_BRICKS;
    }

    @Override
    public double getHardness() {
        return 0.8;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

}
