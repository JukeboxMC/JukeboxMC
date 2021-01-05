package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLantern extends Block {

    public BlockLantern() {
        super( "minecraft:lantern" );
    }

    public void setHanging( boolean value ) {
        this.setState( "hanging", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isHanging() {
        return this.stateExists( "hanging" ) && this.getByteState( "hanging" ) == 1;
    }
}
