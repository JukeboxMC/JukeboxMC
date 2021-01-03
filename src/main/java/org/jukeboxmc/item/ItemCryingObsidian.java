package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCryingObsidian;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCryingObsidian extends Item {

    public ItemCryingObsidian() {
        super( "minecraft:crying_obsidian", -289 );
    }

    @Override
    public Block getBlock() {
        return new BlockCryingObsidian();
    }
}
