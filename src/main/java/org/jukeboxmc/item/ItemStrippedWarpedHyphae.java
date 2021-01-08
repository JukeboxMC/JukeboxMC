package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStrippedWarpedHyphae;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStrippedWarpedHyphae extends Item {

    public ItemStrippedWarpedHyphae() {
        super( "minecraft:stripped_warped_hyphae", -301 );
    }

    @Override
    public BlockStrippedWarpedHyphae getBlock() {
        return new BlockStrippedWarpedHyphae();
    }
}
