package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSoulCampfire;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSoulCampfire extends Item {

    public ItemSoulCampfire() {
        super ( "minecraft:soul_campfire" );
    }

    @Override
    public BlockSoulCampfire getBlock() {
        return new BlockSoulCampfire();
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }
}
