package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockHeavyWeightedPressurePlate extends Block {

    public BlockHeavyWeightedPressurePlate() {
        super( "minecraft:heavy_weighted_pressure_plate" );
    }

    public void setRedstoneSignal( int value ) {
        this.setState( "redstone_signal", value );
    }

    public int getRedstoneSignal() {
        return this.stateExists( "redstone_signal" ) ? this.getIntState( "redstone_signal" ) : 0;
    }
}
