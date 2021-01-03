package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockStandingBanner;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBanner extends Item {

    public ItemBanner() {
        super( "minecraft:banner", 557 );
    }

    @Override
    public Block getBlock() {
        return new BlockStandingBanner();
    }
}
