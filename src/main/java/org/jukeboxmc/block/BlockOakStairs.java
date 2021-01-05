package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockOakStairs extends Block {

    public BlockOakStairs() {
        super( "minecraft:oak_stairs" );
    }

    public void setUpsideDown( boolean value ) {
        this.setState( "upside_down_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isUpsideDown() {
        return this.stateExists( "upside_down_bit" ) && this.getByteState( "upside_down_bit" ) == 1;
    }
}
