package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemHangingRoots;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockHangingRoots extends Block{

    public BlockHangingRoots() {
        super( "minecraft:hanging_roots" );
    }

    @Override
    public ItemHangingRoots toItem() {
        return new ItemHangingRoots();
    }

    @Override
    public BlockType getType() {
        return BlockType.HANGING_ROOTS;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public double getHardness() {
        return 0.1;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.SHEARS;
    }
}
