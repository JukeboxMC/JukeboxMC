package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemChiseledPolishedBlackstone;
import org.jukeboxmc.item.ItemToolType;

public class BlockChiseledPolishedBlackstone extends Block {

    public BlockChiseledPolishedBlackstone() {
        super("minecraft:chiseled_polished_blackstone");
    }

    @Override
    public ItemChiseledPolishedBlackstone toItem() {
        return new ItemChiseledPolishedBlackstone();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CHISELED_POLISHED_BLACKSTONE;
    }

    @Override
    public double getHardness() {
        return 1.5;
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