package org.jukeboxmc.block;

public class BlockSoulLantern extends Block {

    public BlockSoulLantern() {
        super("minecraft:soul_lantern");
    }

    public void setHanging( boolean value ) {
        this.setState( "hanging", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isHanging() {
        return this.stateExists( "hanging" ) && this.getByteState( "hanging" ) == 1;
    }
}