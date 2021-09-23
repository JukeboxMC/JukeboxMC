package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemNetherrack;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockNetherrack extends Block {

    public BlockNetherrack() {
        super( "minecraft:netherrack" );
    }

    @Override
    public ItemNetherrack toItem() {
        return new ItemNetherrack();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.NETHERRACK;
    }

    @Override
    public double getHardness() {
        return 0.4;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

}
