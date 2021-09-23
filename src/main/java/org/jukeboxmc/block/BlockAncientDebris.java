package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemAncientDebris;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

public class BlockAncientDebris extends Block {

    public BlockAncientDebris() {
        super("minecraft:ancient_debris");
    }

    @Override
    public ItemAncientDebris toItem() {
        return new ItemAncientDebris();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ANCIENT_DEBRIS;
    }

    @Override
    public double getHardness() {
        return 30;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public ItemTierType getTierType() {
        return ItemTierType.DIAMOND;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

}