package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemLodestone;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLodestone extends Block {

    public BlockLodestone() {
        super( "minecraft:lodestone" );
    }

    @Override
    public ItemLodestone toItem() {
        return new ItemLodestone();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LODESTONE;
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
