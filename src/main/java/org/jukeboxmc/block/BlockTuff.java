package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemToolType;
import org.jukeboxmc.item.ItemTuff;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockTuff extends Block {

    public BlockTuff() {
        super( "minecraft:tuff" );
    }

    @Override
    public ItemTuff toItem() {
        return new ItemTuff();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.TUFF;
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
    public boolean canBreakWithHand() {
        return false;
    }
}
