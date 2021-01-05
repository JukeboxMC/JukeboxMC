package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockScaffolding extends Block {

    public BlockScaffolding() {
        super( "minecraft:scaffolding" );
    }

    public void setStability( int value ) { //0-7
        this.setState( "stability", value );
    }

    public int getStability() {
        return this.stateExists( "stability" ) ? this.getIntState( "stability" ) : 0;
    }

    public void setStabilityCheck( boolean value ) {
        this.setState( "stability_check", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isStabilityCheck() {
        return this.stateExists( "stability_check" ) && this.getByteState( "stability_check" ) == 1;
    }
}
