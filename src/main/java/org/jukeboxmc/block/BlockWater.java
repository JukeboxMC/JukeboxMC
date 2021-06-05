package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWater;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWater extends BlockLiqued {

    public BlockWater() {
        super( "minecraft:water" );
    }

    @Override
    public ItemWater toItem() {
        return new ItemWater();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WATER;
    }

    @Override
    public boolean canBeReplaced( Block block ) {
        return true;
    }

}
