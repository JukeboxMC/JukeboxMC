package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDetectorRail;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDetectorRail extends Item {

    public ItemDetectorRail() {
        super( "minecraft:detector_rail", 28 );
    }

    @Override
    public Block getBlock() {
        return new BlockDetectorRail();
    }
}
