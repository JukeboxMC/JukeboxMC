package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCoalBlock;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCoal extends Block {

    public BlockCoal() {
        super( "minecraft:coal_block" );
    }

    @Override
    public ItemCoalBlock toItem() {
        return new ItemCoalBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.COAL_BLOCK;
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
