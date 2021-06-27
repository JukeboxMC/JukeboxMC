package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCampfire;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCampfireBlock extends Item {

    public ItemCampfireBlock() {
        super ( "minecraft:item.campfire" );
    }

    @Override
    public BlockCampfire getBlock() {
        return new BlockCampfire();
    }
}
