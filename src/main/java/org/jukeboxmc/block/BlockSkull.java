package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSkull extends Block {

    public BlockSkull() {
        super( "minecraft:skull" );
    }

    public void setNoDrop( boolean value ) {
        this.setState( "no_drop_bit", value ? (byte) 1: (byte) 0 );
    }

    public boolean isNoDrop() {
        return this.stateExists( "no_drop_bit" ) && this.getByteState( "no_drop_bit" ) == 1;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
