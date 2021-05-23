package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemAncientDebris;

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

}