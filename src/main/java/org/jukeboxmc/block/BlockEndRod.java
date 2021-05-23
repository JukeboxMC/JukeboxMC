package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.ItemEndRod;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEndRod extends Block {

    public BlockEndRod() {
        super( "minecraft:end_rod" );
    }

    @Override
    public ItemEndRod toItem() {
        return new ItemEndRod();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.END_ROD;
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
