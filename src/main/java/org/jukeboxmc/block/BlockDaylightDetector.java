package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDaylightDetector extends Block {

    public BlockDaylightDetector() {
        super( "minecraft:daylight_detector" );
    }

    public void setRedstoneSignal( int value ) {
        this.setState( "redstone_signal", value );
    }

    public int getRedstoneSignal() {
        return this.stateExists( "redstone_signal" ) ? this.getIntState( "redstone_signal" ) : 0;
    }
}
