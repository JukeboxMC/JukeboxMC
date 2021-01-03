package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCobblestone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCobblestone extends Item {

    public ItemCobblestone() {
        super("minecraft:cobblestone", 4);
    }

    @Override
    public Block getBlock() {
        return new BlockCobblestone();
    }
}
