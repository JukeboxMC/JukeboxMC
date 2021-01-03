package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCampfire;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCampfireBlock extends Item {

    public ItemCampfireBlock() {
        super( "minecraft:item.campfire", -209 );
    }

    @Override
    public Block getBlock() {
        return new BlockCampfire();
    }
}
