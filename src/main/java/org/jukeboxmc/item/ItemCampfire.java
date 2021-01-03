package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCampfire;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCampfire extends Item {

    public ItemCampfire() {
        super( "minecraft:campfire", 578 );
    }

    @Override
    public Block getBlock() {
        return new BlockCampfire();
    }
}
