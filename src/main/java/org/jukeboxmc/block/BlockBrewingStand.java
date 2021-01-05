package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBrewingStand extends Block {

    public BlockBrewingStand() {
        super( "minecraft:brewing_stand" );
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
