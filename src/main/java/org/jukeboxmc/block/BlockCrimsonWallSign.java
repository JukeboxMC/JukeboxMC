package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;

public class BlockCrimsonWallSign extends Block {

    public BlockCrimsonWallSign() {
        super("minecraft:crimson_wall_sign");
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}