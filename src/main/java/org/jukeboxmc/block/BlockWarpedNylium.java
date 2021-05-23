package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWarpedNylium;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWarpedNylium extends Block {

    public BlockWarpedNylium() {
        super( "minecraft:warped_nylium" );
    }

    @Override
    public ItemWarpedNylium toItem() {
        return new ItemWarpedNylium();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WARPED_NYLIUM;
    }

}
