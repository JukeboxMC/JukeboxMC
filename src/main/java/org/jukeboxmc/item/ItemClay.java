package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockClay;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemClay extends Item {

    public ItemClay() {
        super( "minecraft:clay", 82 );
    }

    @Override
    public Block getBlock() {
        return new BlockClay();
    }
}
