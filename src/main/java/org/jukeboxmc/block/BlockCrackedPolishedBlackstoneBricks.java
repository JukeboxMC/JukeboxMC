package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCrackedPolishedBlackstoneBricks;
import org.jukeboxmc.item.type.ItemToolType;

public class BlockCrackedPolishedBlackstoneBricks extends Block {

    public BlockCrackedPolishedBlackstoneBricks() {
        super("minecraft:cracked_polished_blackstone_bricks");
    }

    @Override
    public ItemCrackedPolishedBlackstoneBricks toItem() {
        return new ItemCrackedPolishedBlackstoneBricks();
    }

    @Override
    public BlockType getType() {
        return BlockType.CRACKED_POLISHED_BLACKSTONE_BRICKS;
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