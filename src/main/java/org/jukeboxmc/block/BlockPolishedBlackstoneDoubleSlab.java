package org.jukeboxmc.block;

public class BlockPolishedBlackstoneDoubleSlab extends Block {

    public BlockPolishedBlackstoneDoubleSlab() {
        super("minecraft:polished_blackstone_double_slab");
    }

    public void setTopSlot( boolean value ) {
        this.setState( "top_slot_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isTopSlot() {
        return this.stateExists( "top_slot_bit" ) && this.getByteState( "top_slot_bit" ) == 1;
    }
}