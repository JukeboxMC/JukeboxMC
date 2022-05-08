package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemRedSandstoneStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRedSandstoneStairs extends BlockStairs {

    public BlockRedSandstoneStairs() {
        super( "minecraft:red_sandstone_stairs" );
    }

    @Override
    public ItemRedSandstoneStairs toItem() {
        return new ItemRedSandstoneStairs();
    }

    @Override
    public BlockType getType() {
        return BlockType.RED_SANDSTONE_STAIRS;
    }

}
