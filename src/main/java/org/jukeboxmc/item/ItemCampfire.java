package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCampfire;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCampfire extends Item {

    public ItemCampfire() {
        super( "minecraft:campfire" );
    }

    @Override
    public BlockCampfire getBlock() {
        return new BlockCampfire();
    }
}
