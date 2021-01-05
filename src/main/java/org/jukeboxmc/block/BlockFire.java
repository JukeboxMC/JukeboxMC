package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFire extends Block {

    public BlockFire() {
        super( "minecraft:fire" );
    }

    public void setAge( int value ) { // 0-15
        this.setState( "age", value );
    }

    public int getAge() {
        return this.stateExists( "age" ) ? this.getIntState( "age" ) : 0;
    }
}
