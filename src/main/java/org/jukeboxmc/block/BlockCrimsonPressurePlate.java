package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCrimsonPressurePlate;

public class BlockCrimsonPressurePlate extends Block {

    public BlockCrimsonPressurePlate() {
        super("minecraft:crimson_pressure_plate");
    }

    @Override
    public ItemCrimsonPressurePlate toItem() {
        return new ItemCrimsonPressurePlate();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CRIMSON_PRESSURE_PLATE;
    }

    public void setRedstoneSignal( int value ) {
        this.setState( "redstone_signal", value );
    }

    public int getRedstoneSignal() {
        return this.stateExists( "redstone_signal" ) ? this.getIntState( "redstone_signal" ) : 0;
    }
}