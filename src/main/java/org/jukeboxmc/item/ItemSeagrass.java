package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSeagrass;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSeagrass extends Item {

    public ItemSeagrass() {
        super( "minecraft:seagrass", -130 );
    }

    @Override
    public BlockSeagrass getBlock() {
        return new BlockSeagrass();
    }
}
