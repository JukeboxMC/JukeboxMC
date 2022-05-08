package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemLapisBlock;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLapisBlock extends Block {

    public BlockLapisBlock() {
        super( "minecraft:lapis_block" );
    }

    @Override
    public ItemLapisBlock toItem() {
        return new ItemLapisBlock();
    }

    @Override
    public BlockType getType() {
        return BlockType.LAPIS_BLOCK;
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
