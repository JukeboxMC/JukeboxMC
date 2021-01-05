package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFarmland extends Block {

    public BlockFarmland() {
        super( "minecraft:farmland" );
    }

    public void setMoisturizedAmount( int value ) {
        this.setState( "moisturized_amount", value );
    }

    public int getMoisturizedAmount() {
        return this.stateExists( "moisturized_amount" ) ? this.getIntState( "moisturized_amount" ) : 0;
    }
}
