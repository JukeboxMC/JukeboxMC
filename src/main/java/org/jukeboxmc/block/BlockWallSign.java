package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.ItemWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWallSign extends Block {

    public BlockWallSign() {
        super( "minecraft:wall_sign" );
    }

    @Override
    public ItemWallSign toItem() {
        return new ItemWallSign();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WALL_SIGN;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
