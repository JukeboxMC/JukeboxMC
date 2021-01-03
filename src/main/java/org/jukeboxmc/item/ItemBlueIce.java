package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBlueIce;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBlueIce extends Item {

    public ItemBlueIce() {
        super( "minecraft:blue_ice", -11 );
    }

    @Override
    public Block getBlock() {
        return new BlockBlueIce();
    }
}
