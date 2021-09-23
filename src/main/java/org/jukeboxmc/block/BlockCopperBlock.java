package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCopperBlock;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCopperBlock extends Block {

    public BlockCopperBlock() {
        super( "minecraft:copper_block" );
    }

    @Override
    public ItemCopperBlock toItem() {
        return new ItemCopperBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.COPPER_BLOCK;
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
        return ItemTierType.STONE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }
}
