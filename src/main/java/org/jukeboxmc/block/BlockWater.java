package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWater;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWater extends BlockLiquid {

    public BlockWater() {
        super( "minecraft:water" );
    }

    protected BlockWater( String identifier ) {
        super( identifier );
    }

    @Override
    public ItemWater toItem() {
        return new ItemWater();
    }

    @Override
    public BlockType getType() {
        return BlockType.WATER;
    }

    @Override
    public boolean canBeReplaced( Block block ) {
        return true;
    }

    @Override
    public int getTickRate() {
        return 5;
    }

    @Override
    public boolean usesWaterLogging() {
        return true;
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    @Override
    public BlockLiquid getBlock( int liquidDepth ) {
        BlockWater blockWater = new BlockWater();
        blockWater.setLiquidDepth( liquidDepth );
        return blockWater;
    }
}
