package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSnowLayer extends Block {

    public BlockSnowLayer() {
        super( "minecraft:snow_layer" );
    }

    public void setCovered( boolean value ) {
        this.setState( "covered_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isCovered() {
        return this.stateExists( "covered_bit" ) && this.getByteState( "covered_bit" ) == 1;
    }

    public void setHeight( int value ) { //0-7
        this.setState( "height", value );
    }

    public int getHeight() {
        return this.stateExists( "height" ) ? this.getIntState( "height" ) : 0;
    }
}
