package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCrimsonNylium;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonNylium extends Item {

    public ItemCrimsonNylium() {
        super( "minecraft:crimson_nylium", -232 );
    }

    @Override
    public Block getBlock() {
        return new BlockCrimsonNylium();
    }
}
