package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFrostedIce extends Block {

    public BlockFrostedIce() {
        super( "minecraft:frosted_ice" );
    }

    public void setAge( int value ) {
        this.setState( "age", value );
    }

    public int getAge() {
        return this.stateExists( "age" ) ? this.getIntState( "age" ) : 0;
    }
}
