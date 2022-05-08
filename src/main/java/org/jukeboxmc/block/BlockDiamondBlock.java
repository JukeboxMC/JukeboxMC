package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDiamondBlock;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDiamondBlock extends Block {

    public BlockDiamondBlock() {
        super( "minecraft:diamond_block" );
    }

    @Override
    public ItemDiamondBlock toItem() {
        return new ItemDiamondBlock();
    }

    @Override
    public BlockType getType() {
        return BlockType.DIAMOND_BLOCK;
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
        return ItemTierType.IRON;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

}
