package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSoulFire extends Block {

    public BlockSoulFire() {
        super( "minecraft:soul_fire" );
    }

    public void setAge( int value ) {
        this.setState( "age", value );
    }

    public int getAge() {
        return this.stateExists( "age" ) ? this.getIntState( "age" ) : 0;
    }
}
