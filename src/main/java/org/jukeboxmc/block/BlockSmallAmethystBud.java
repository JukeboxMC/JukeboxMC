package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.ItemSmallAmethystBud;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSmallAmethystBud extends Block{

    public BlockSmallAmethystBud() {
        super( "minecraft:small_amethyst_bud" );
    }

    @Override
    public ItemSmallAmethystBud toItem() {
        return new ItemSmallAmethystBud();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SMALL_AMETHYST_BUD;
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
