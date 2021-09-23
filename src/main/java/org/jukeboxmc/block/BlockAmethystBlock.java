package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemAmethystBlock;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAmethystBlock extends Block{

    public BlockAmethystBlock() {
        super( "minecraft:amethyst_block" );
    }

    @Override
    public ItemAmethystBlock toItem() {
        return new ItemAmethystBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.AMETHYST_BLOCK;
    }

    @Override
    public double getHardness() {
        return 1.5;
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
