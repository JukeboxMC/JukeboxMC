package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDirtWithRoots;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDirtWithRoots extends Block {

    public BlockDirtWithRoots() {
        super( "minecraft:dirt_with_roots" );
    }

    @Override
    public ItemDirtWithRoots toItem() {
        return new ItemDirtWithRoots();
    }

    @Override
    public BlockType getType() {
        return BlockType.DIRT_WITH_ROOTS;
    }

    @Override
    public double getHardness() {
        return 0.5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }
}
