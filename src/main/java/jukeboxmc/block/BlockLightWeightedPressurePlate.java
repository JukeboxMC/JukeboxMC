package jukeboxmc.block;

import org.jukeboxmc.item.ItemLightWeightedPressurePlate;

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
    public BlockType getBlockType() {
        return BlockType.LIGHT_WEIGHTED_PRESSURE_PLATE;
    }

    public void setRedstoneSignal( int value ) {
        this.setState( "redstone_signal", value );
    }

    public int getRedstoneSignal() {
        return this.stateExists( "redstone_signal" ) ? this.getIntState( "redstone_signal" ) : 0;
    }
}
