package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockNetherWart extends Block {

    public BlockNetherWart() {
        super( "minecraft:nether_wart" );
    }

    public void setAge( int value ) {
        this.setState( "age", value );
    }

    public int getAge() {
        return this.stateExists( "age" ) ? this.getIntState( "age" ) : 0;
    }
}
