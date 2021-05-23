package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemTwistingVines;

public class BlockTwistingVines extends Block {

    public BlockTwistingVines() {
        super("minecraft:twisting_vines");
    }

    @Override
    public ItemTwistingVines toItem() {
        return new ItemTwistingVines();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.TWISTING_VINES;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    public void setTwistingVinesAge( int value ) { //0-25
        this.setState( "twisting_vines_age", value );
    }

    public int getTwistingVinesAge() {
        return this.stateExists( "twisting_vines_age" ) ? this.getIntState( "twisting_vines_age" ) : 0;
    }
}