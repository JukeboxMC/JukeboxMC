package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockMossyCobblestone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemMossyCobblestone extends Item {

    public ItemMossyCobblestone() {
        super( "minecraft:mossy_cobblestone", 48 );
    }

    @Override
    public BlockMossyCobblestone getBlock() {
        return new BlockMossyCobblestone();
    }
}
