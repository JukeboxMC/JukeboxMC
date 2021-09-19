package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBrewingStand;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBrewingStand extends BlockWaterlogable {

    public BlockBrewingStand() {
        super( "minecraft:brewing_stand" );
    }

    @Override
    public ItemBrewingStand toItem() {
        return new ItemBrewingStand();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BREWING_STAND;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public double getHardness() {
        return 0.5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    public void setBrewingStandSlotA( boolean value ) {
        this.setState( "brewing_stand_slot_a_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isBrewingStandSlotA() {
        return this.stateExists( "brewing_stand_slot_a_bit" ) && this.getByteState( "brewing_stand_slot_a_bit" ) == 1;
    }

    public void setBrewingStandSlotB( boolean value ) {
        this.setState( "brewing_stand_slot_a_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isBrewingStandSlotB() {
        return this.stateExists( "brewing_stand_slot_b_bit" ) && this.getByteState( "brewing_stand_slot_b_bit" ) == 1;
    }

    public void setBrewingStandSlotC( boolean value ) {
        this.setState( "brewing_stand_slot_a_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isBrewingStandSlotC() {
        return this.stateExists( "brewing_stand_slot_c_bit" ) && this.getByteState( "brewing_stand_slot_c_bit" ) == 1;
    }
}
