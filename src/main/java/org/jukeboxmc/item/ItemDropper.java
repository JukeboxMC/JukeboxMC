package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDropper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDropper extends Item {

    public ItemDropper() {
        super( "minecraft:dropper", 125 );
    }

    @Override
    public Block getBlock() {
        return new BlockDropper();
    }
}
