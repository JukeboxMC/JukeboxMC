package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockClay;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemClay extends Item {

    public ItemClay() {
        super ( "minecraft:clay" );
    }

    @Override
    public BlockClay getBlock() {
        return new BlockClay();
    }
}
