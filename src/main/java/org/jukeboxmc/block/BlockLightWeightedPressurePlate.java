package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLightWeightedPressurePlate extends Block {

    public BlockLightWeightedPressurePlate() {
        super( "minecraft:light_weighted_pressure_plate" );
    }

    public void setRedstoneSignal( int value ) {
        this.setState( "redstone_signal", value );
    }

    public int getRedstoneSignal() {
        return this.stateExists( "redstone_signal" ) ? this.getIntState( "redstone_signal" ) : 0;
    }
}
