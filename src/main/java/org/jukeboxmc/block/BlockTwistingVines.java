package org.jukeboxmc.block;

public class BlockTwistingVines extends Block {

    public BlockTwistingVines() {
        super("minecraft:twisting_vines");
    }

    public void setTwistingVinesAge( int value ) { //0-25
        this.setState( "twisting_vines_age", value );
    }

    public int getTwistingVinesAge() {
        return this.stateExists( "twisting_vines_age" ) ? this.getIntState( "twisting_vines_age" ) : 0;
    }
}