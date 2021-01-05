package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWater extends Block {

    public BlockWater() {
        super( "minecraft:water" );
    }

    public void setLiquidDepth( int value ) {
        this.setState( "liquid_depth", value );
    }

    public int getLiquidDepth() {
        return this.stateExists( "liquid_depth" ) ? this.getIntState( "liquid_depth" ) : 0;
    }
}
