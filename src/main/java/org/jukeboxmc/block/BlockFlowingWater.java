package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWater;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFlowingWater extends BlockWater {

    public BlockFlowingWater() {
        super( "minecraft:flowing_water" );
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

    @Override
    public BlockLiquid getBlock( int liquidDepth ) {
        BlockFlowingWater blockFlowingWater = new BlockFlowingWater();
        blockFlowingWater.setLiquidDepth( liquidDepth );
        return blockFlowingWater;
    }
}
