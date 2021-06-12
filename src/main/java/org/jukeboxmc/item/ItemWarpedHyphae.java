package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedHyphae;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedHyphae extends Item {

    public ItemWarpedHyphae() {
        super ( "minecraft:warped_hyphae" );
    }

    @Override
    public BlockWarpedHyphae getBlock() {
        return new BlockWarpedHyphae();
    }
}
