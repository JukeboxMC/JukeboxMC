package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWallSign extends Block {

    public BlockWallSign() {
        super( "minecraft:wall_sign" );
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
