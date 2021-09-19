package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemGoldBlock;
import org.jukeboxmc.item.ItemTierType;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGoldBlock extends Block {

    public BlockGoldBlock() {
        super( "minecraft:gold_block" );
    }

    @Override
    public ItemGoldBlock toItem() {
        return new ItemGoldBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GOLD_BLOCK;
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
    public ItemTierType getTierType() {
        return ItemTierType.IRON;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }
}
