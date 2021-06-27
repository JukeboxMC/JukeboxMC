package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.ItemMediumAmethystBud;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMediumAmethystBud extends Block {

    public BlockMediumAmethystBud() {
        super("minecraft:medium_amethyst_bud");
    }

    @Override
    public ItemMediumAmethystBud toItem() {
        return new ItemMediumAmethystBud();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.MEDIUM_AMETHYST_BUD;
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
