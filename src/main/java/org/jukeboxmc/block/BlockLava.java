package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLava extends Block {

    public BlockLava() {
        super( "minecraft:lava" );
    }

    public void setLiquidDepth( int value ) {
        this.setState( "liquid_depth", value );
    }

    public int getLiquidDepth() {
        return this.stateExists( "liquid_depth" ) ? this.getIntState( "liquid_depth" ) : 0;
    }
}
