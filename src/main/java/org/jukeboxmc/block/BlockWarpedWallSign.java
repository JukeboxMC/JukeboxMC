package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;

public class BlockWarpedWallSign extends Block {

    public BlockWarpedWallSign() {
        super("minecraft:warped_wall_sign");
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}