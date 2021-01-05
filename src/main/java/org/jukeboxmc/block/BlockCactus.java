package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCactus extends Block {

    public BlockCactus() {
        super( "minecraft:cactus" );
    }

    public void setAge( int value ) { //0-15
        this.setState( "age", value );
    }

    public int getAge() {
        return this.stateExists( "age" ) ? this.getIntState( "age" ) : 0;
    }
}
