package org.jukeboxmc.block;

import org.jukeboxmc.block.type.UpdateReason;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class BlockLiqued extends Block {

    public BlockLiqued( String identifier ) {
        super( identifier );
    }

    @Override
    public long onUpdate( UpdateReason updateReason ) {
        if ( updateReason.equals( UpdateReason.NORMAL ) ) {
        }
        return -1;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    public BlockLiqued setLiquidDepth( int value ) {
        return this.setState( "liquid_depth", value );
    }

    public int getLiquidDepth() {
        return this.stateExists( "liquid_depth" ) ? this.getIntState( "liquid_depth" ) : 0;
    }
}
