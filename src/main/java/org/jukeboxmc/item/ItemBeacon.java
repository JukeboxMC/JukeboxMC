package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBeacon;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBeacon extends Item {

    public ItemBeacon() {
        super( "minecraft:beacon", 138 );
    }

    @Override
    public Block getBlock() {
        return new BlockBeacon();
    }
}
