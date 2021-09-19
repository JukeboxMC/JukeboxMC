package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemRawIronBlock;
import org.jukeboxmc.item.ItemTierType;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRawIronBlock extends Block {
    
    public BlockRawIronBlock() {
        super( "minecraft:raw_iron_block" );
    }

    @Override
    public ItemRawIronBlock toItem() {
        return new ItemRawIronBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.RAW_IRON_BLOCK;
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
