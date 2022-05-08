package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemIronBlock;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockIronBlock extends Block {

    public BlockIronBlock() {
        super( "minecraft:iron_block" );
    }

    @Override
    public ItemIronBlock toItem() {
        return new ItemIronBlock();
    }

    @Override
    public BlockType getType() {
        return BlockType.IRON_BLOCK;
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
