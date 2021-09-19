package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemRawGoldBlock;
import org.jukeboxmc.item.ItemTierType;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRawGoldBlock extends Block{

    public BlockRawGoldBlock() {
        super( "minecraft:raw_gold_block" );
    }

    @Override
    public ItemRawGoldBlock toItem() {
        return new ItemRawGoldBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.RAW_GOLD_BLOCK;
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
