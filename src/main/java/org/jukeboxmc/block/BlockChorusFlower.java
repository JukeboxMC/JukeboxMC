package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockChorusFlower extends Block {

    public BlockChorusFlower() {
        super( "minecraft:chorus_flower" );
    }

    public void setAge( int value ) {
        this.setState( "age", value );
    }

    public int getAge() {
        return this.stateExists( "age" ) ? this.getIntState( "age" ) : 0;
    }
}
