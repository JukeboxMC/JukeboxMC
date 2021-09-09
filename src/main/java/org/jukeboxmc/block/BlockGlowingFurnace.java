package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.ItemFurnace;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGlowingFurnace extends Block {

    public BlockGlowingFurnace() {
        super( "minecraft:lit_furnace" );
    }

    @Override
    public ItemFurnace toItem() {
        return new ItemFurnace();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.FURNACE;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
