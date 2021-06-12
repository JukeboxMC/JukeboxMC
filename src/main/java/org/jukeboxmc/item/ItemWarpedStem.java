package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedStem;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedStem extends Item {

    public ItemWarpedStem() {
        super ( "minecraft:warped_stem" );
    }

    @Override
    public BlockWarpedStem getBlock() {
        return new BlockWarpedStem();
    }
}
