package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWallBanner;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWallBanner extends Item {

    public ItemWallBanner() {
        super( "minecraft:wall_banner", 177 );
    }

    @Override
    public BlockWallBanner getBlock() {
        return new BlockWallBanner();
    }
}
