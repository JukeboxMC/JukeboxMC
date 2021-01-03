package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockActivatorRail;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemActivatorRail extends Item {

    public ItemActivatorRail() {
        super( "minecraft:activator_rail", 126 );
    }

    @Override
    public Block getBlock() {
        return new BlockActivatorRail();
    }
}
