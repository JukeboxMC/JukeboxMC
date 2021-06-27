package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.ItemLargeAmethystBud;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLargeAmethystBud extends Block {

    public BlockLargeAmethystBud() {
        super( "minecraft:large_amethyst_bud" );
    }

    @Override
    public ItemLargeAmethystBud toItem() {
        return new ItemLargeAmethystBud();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LARGE_AMETHYST_BUD;
    }

    @Override
    public boolean isTransparent() {
        return true;
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
