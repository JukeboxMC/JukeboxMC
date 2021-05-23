package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.ItemAcaciaWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAcaciaWallSign extends Block {

    public BlockAcaciaWallSign() {
        super( "minecraft:acacia_wall_sign" );
    }

    @Override
    public ItemAcaciaWallSign toItem() {
        return new ItemAcaciaWallSign();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ACACIA_WALL_SIGN;
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
