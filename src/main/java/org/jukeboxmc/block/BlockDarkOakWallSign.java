package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.ItemDarkOakWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDarkOakWallSign extends Block {

    public BlockDarkOakWallSign() {
        super( "minecraft:darkoak_wall_sign" );
    }

    @Override
    public ItemDarkOakWallSign toItem() {
        return new ItemDarkOakWallSign();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DARK_OAK_WALL_SIGN;
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
