package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSoulCampfire;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSoulCampfireBlock extends Item {

    public ItemSoulCampfireBlock() {
        super( -290 );
    }

    @Override
    public BlockSoulCampfire getBlock() {
        return new BlockSoulCampfire();
    }
}
