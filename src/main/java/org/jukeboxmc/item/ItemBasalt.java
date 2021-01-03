package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBasalt;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBasalt extends Item {

    public ItemBasalt() {
        super( "minecraft:basalt", -234 );
    }

    @Override
    public Block getBlock() {
        return new BlockBasalt();
    }
}
