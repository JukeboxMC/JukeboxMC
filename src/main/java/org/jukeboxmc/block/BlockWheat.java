package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWheat extends Block {

    public BlockWheat() {
        super( "minecraft:wheat" );
    }

    public void setGrowth( int value ) { //0-7
        this.setState( "growth", value );
    }

    public int getGrowth() {
        return this.stateExists( "growth" ) ? this.getIntState( "growth" ) : 0;
    }
}
