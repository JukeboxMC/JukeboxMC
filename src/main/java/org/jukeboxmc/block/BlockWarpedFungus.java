package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWarpedFungus;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWarpedFungus extends Block {

    public BlockWarpedFungus() {
        super( "minecraft:warped_fungus" );
    }

    @Override
    public ItemWarpedFungus toItem() {
        return new ItemWarpedFungus();
    }

    @Override
    public BlockType getType() {
        return BlockType.WARPED_FUNGUS;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}
