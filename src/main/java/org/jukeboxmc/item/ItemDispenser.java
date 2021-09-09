package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDispenser;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDispenser extends Item {

    public ItemDispenser() {
        super ( "minecraft:dispenser" );
    }

    @Override
    public BlockDispenser getBlock() {
        return new BlockDispenser();
    }
}
