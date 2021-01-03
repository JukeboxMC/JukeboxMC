package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBarrier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBarrier extends Item {

    public ItemBarrier() {
        super( "minecraft:barrier", -161 );
    }

    @Override
    public Block getBlock() {
        return new BlockBarrier();
    }
}
