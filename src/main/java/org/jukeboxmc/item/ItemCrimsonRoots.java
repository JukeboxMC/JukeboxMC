package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCrimsonRoots;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonRoots extends Item {

    public ItemCrimsonRoots() {
        super( "minecraft:crimson_roots", -223 );
    }

    @Override
    public Block getBlock() {
        return new BlockCrimsonRoots();
    }
}
