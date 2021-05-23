package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWater;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFlowingWater extends Block {

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

    public void setLiquidDepth( int value ) {
        this.setState( "liquid_depth", value );
    }

    public int getLiquidDepth() {
        return this.stateExists( "liquid_depth" ) ? this.getIntState( "liquid_depth" ) : 0;
    }
}
