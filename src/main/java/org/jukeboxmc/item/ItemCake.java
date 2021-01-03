package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCake;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCake extends Item {

    public ItemCake() {
        super( "minecraft:cake", 415 );
    }

    @Override
    public Block getBlock() {
        return new BlockCake();
    }
}
