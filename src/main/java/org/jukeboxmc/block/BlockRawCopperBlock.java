package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemRawCopperBlock;
import org.jukeboxmc.item.ItemTierType;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRawCopperBlock extends Block {

    public BlockRawCopperBlock() {
        super( "minecraft:raw_copper_block" );
    }

    @Override
    public ItemRawCopperBlock toItem() {
        return new ItemRawCopperBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.RAW_COPPER_BLOCK;
    }

    @Override
    public double getHardness() {
        return 5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public ItemTierType getTierType() {
        return ItemTierType.STONE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }
}
