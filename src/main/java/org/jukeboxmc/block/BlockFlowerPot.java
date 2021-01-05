package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFlowerPot extends Block {

    public BlockFlowerPot() {
        super( "minecraft:flower_pot" );
    }

    public void setUpdate( boolean value ) {
        this.setState( "update_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isUpdate() {
        return this.stateExists( "update_bit" ) && this.getByteState( "update_bit" ) == 1;
    }

}
