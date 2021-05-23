package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.ItemBirchWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBirchWallSign extends Block {

    public BlockBirchWallSign() {
        super( "minecraft:birch_wall_sign" );
    }

    @Override
    public ItemBirchWallSign toItem() {
        return new ItemBirchWallSign();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BIRCH_WALL_SIGN;
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
