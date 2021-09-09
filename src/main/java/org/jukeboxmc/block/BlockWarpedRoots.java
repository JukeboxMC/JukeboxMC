package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWarpedRoots;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWarpedRoots extends Block {

    public BlockWarpedRoots() {
        super( "minecraft:warped_roots" );
    }

    @Override
    public ItemWarpedRoots toItem() {
        return new ItemWarpedRoots();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WARPED_ROOTS;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}
