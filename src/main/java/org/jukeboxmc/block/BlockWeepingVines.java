package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWeepingVines extends Block {

    public BlockWeepingVines() {
        super( "minecraft:weeping_vines" );
    }

    public void setWeepingVinesAge( int value ) {
        this.setState( "weeping_vines_age", value );
    }

    public int getWeepingVinesAge() {
        return this.stateExists( "weeping_vines_age" ) ? this.getIntState( "weeping_vines_age" ) : 0;
    }
}
