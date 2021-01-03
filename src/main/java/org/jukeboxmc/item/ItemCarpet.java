package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCampfire;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCarpet extends Item {

    public ItemCarpet() {
        super( "minecraft:carpet", 171 );
    }

    @Override
    public Block getBlock() {
        return new BlockCampfire();
    }
}
