package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDaylightDetector;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDaylightDetector extends Item {

    public ItemDaylightDetector() {
        super( "minecraft:daylight_detector", 151 );
    }

    @Override
    public Block getBlock() {
        return new BlockDaylightDetector();
    }
}
