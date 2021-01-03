package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCarrots;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCarrots extends Item {

    public ItemCarrots() {
        super( "minecraft:carrots", 141 );
    }

    @Override
    public Block getBlock() {
        return new BlockCarrots();
    }
}
