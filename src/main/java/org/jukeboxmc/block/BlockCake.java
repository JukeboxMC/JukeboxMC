package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCake extends Block {

    public BlockCake() {
        super( "minecraft:cake" );
    }

    public void setBiteCounter( int value ) {
        this.setState( "bite_counter", value );
    }

    public int getBiteCounter() {
        return this.stateExists( "bite_counter" ) ? this.getIntState( "bite_counter" ) : 0;
    }
}
