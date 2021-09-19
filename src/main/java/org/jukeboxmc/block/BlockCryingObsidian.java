package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCryingObsidian;
import org.jukeboxmc.item.ItemTierType;
import org.jukeboxmc.item.ItemToolType;

public class BlockCryingObsidian extends Block {

    public BlockCryingObsidian() {
        super("minecraft:crying_obsidian");
    }

    @Override
    public ItemCryingObsidian toItem() {
        return new ItemCryingObsidian();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CRYING_OBSIDIAN;
    }

    @Override
    public double getHardness() {
        return 35;
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