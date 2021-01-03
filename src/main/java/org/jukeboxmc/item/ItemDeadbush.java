package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDeadbush;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDeadbush extends Item {

    public ItemDeadbush() {
        super( "minecraft:deadbush", 32 );
    }

    @Override
    public Block getBlock() {
        return new BlockDeadbush();
    }
}
