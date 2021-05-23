package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.ItemJungleWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockJungleWallSign extends Block {

    public BlockJungleWallSign() {
        super( "minecraft:jungle_wall_sign" );
    }

    @Override
    public ItemJungleWallSign toItem() {
        return new ItemJungleWallSign();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.JUNGLE_WALL_SIGN;
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
