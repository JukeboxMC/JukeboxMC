package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCrimsonHyphae;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonHyphae extends Item {

    public ItemCrimsonHyphae() {
        super( "minecraft:crimson_hyphae", -299 );
    }

    @Override
    public Block getBlock() {
        return new BlockCrimsonHyphae();
    }
}
