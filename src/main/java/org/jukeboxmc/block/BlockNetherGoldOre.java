package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemNetherGoldOre;
import org.jukeboxmc.item.type.ItemToolType;

public class BlockNetherGoldOre extends Block {

    public BlockNetherGoldOre() {
        super("minecraft:nether_gold_ore");
    }

    @Override
    public ItemNetherGoldOre toItem() {
        return new ItemNetherGoldOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.NETHER_GOLD_ORE;
    }

    @Override
    public double getHardness() {
        return 3;
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