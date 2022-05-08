package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.blockentity.BlockEntitySign;
import org.jukeboxmc.item.ItemSpruceWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSpruceWallSign extends Block {

    public BlockSpruceWallSign() {
        super( "minecraft:spruce_wall_sign" );
    }

    @Override
    public ItemSpruceWallSign toItem() {
        return new ItemSpruceWallSign();
    }

    @Override
    public BlockType getType() {
        return BlockType.SPRUCE_WALL_SIGN;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

    @Override
    public BlockEntitySign getBlockEntity() {
        return (BlockEntitySign) this.world.getBlockEntity( this.location, this.location.getDimension() );
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
