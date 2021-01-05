package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSugarCane extends Block {

    public BlockSugarCane() {
        super( "minecraft:reeds" );
    }

    public void setAge( int value ) {
        this.setState( "age", value );
    }

    public int getAge() {
        return this.stateExists( "age" ) ? this.getIntState( "age" ) : 0;
    }
}
