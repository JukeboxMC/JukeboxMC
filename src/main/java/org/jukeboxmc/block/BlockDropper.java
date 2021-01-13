package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDropper extends Block {

    public BlockDropper() {
        super( "minecraft:dropper" );
    }

    public void setTriggered( boolean value ) {
        this.setState( "triggered_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isTriggered() {
        return this.stateExists( "triggered_bit" ) && this.getByteState( "triggered_bit" ) == 1;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
