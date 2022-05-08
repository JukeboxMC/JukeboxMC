package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemLightWeightedPressurePlate;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLightWeightedPressurePlate extends BlockPressurePlate {

    public BlockLightWeightedPressurePlate() {
        super( "minecraft:light_weighted_pressure_plate" );
    }

    @Override
    public ItemLightWeightedPressurePlate toItem() {
        return new ItemLightWeightedPressurePlate();
    }

    @Override
    public BlockType getType() {
        return BlockType.LIGHT_WEIGHTED_PRESSURE_PLATE;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    public void setRedstoneSignal( int value ) {
        this.setState( "redstone_signal", value );
    }

    public int getRedstoneSignal() {
        return this.stateExists( "redstone_signal" ) ? this.getIntState( "redstone_signal" ) : 0;
    }
}
