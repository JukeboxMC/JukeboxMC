package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemEndStone;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEndStone extends Block {

    public BlockEndStone() {
        super( "minecraft:end_stone" );
    }

    @Override
    public ItemEndStone toItem() {
        return new ItemEndStone();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.END_STONE;
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
