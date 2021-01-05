package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockVine extends Block {

    public BlockVine() {
        super( "minecraft:vine" );
    }

    public void setVineDirection( int value ) { //0-15 TODO -> Add Direction Class
        this.setState( "vine_direction_bits", value );
    }

    public int getVineDirection() {
        return this.stateExists( "vine_direction_bits" ) ? this.getIntState( "vine_direction_bits" ) : 0;
    }
}
