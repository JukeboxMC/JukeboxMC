package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBrickBlock;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBrick extends Block {

    public BlockBrick() {
        super( "minecraft:brick_block" );
    }

    @Override
    public ItemBrickBlock toItem() {
        return new ItemBrickBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BRICK_BLOCK;
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
