package org.jukeboxmc.block;

public class BlockCrimsonPressurePlate extends Block {

    public BlockCrimsonPressurePlate() {
        super("minecraft:crimson_pressure_plate");
    }

    public void setRedstoneSignal( int value ) {
        this.setState( "redstone_signal", value );
    }

    public int getRedstoneSignal() {
        return this.stateExists( "redstone_signal" ) ? this.getIntState( "redstone_signal" ) : 0;
    }
}