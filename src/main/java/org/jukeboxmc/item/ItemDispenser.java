package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDispenser;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDispenser extends Item {

    public ItemDispenser() {
        super( "minecraft:dispenser", 23 );
    }

    @Override
    public Block getBlock() {
        return new BlockDispenser();
    }
}
