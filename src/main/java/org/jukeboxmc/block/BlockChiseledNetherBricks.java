package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemChiseledNetherBricks;
import org.jukeboxmc.item.type.ItemToolType;

public class BlockChiseledNetherBricks extends Block {

    public BlockChiseledNetherBricks() {
        super("minecraft:chiseled_nether_bricks");
    }

    @Override
    public ItemChiseledNetherBricks toItem() {
        return new ItemChiseledNetherBricks();
    }

    @Override
    public BlockType getType() {
        return BlockType.CHISELED_NETHER_BRICKS;
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