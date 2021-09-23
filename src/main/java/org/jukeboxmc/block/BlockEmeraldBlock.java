package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemEmeraldBlock;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEmeraldBlock extends Block {

    public BlockEmeraldBlock() {
        super( "minecraft:emerald_block" );
    }

    @Override
    public ItemEmeraldBlock toItem() {
        return new ItemEmeraldBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.EMERALD_BLOCK;
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
