package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemAcaciaPressurePlate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAcaciaPressurePlate extends BlockPressurePlate {

    public BlockAcaciaPressurePlate() {
        super( "minecraft:acacia_pressure_plate" );
    }

    public void setRedstoneSignal( int value ) {
        this.setState( "redstone_signal", value );
    }

    public int getRedstoneSignal() {
        return this.stateExists( "redstone_signal" ) ? this.getIntState( "redstone_signal" ) : 0;
    }

    @Override
    public ItemAcaciaPressurePlate toItem() {
        return new ItemAcaciaPressurePlate();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ACACIA_PRESSURE_PLATE;
    }

}
