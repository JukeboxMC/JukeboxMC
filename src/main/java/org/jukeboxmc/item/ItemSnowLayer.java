package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSnowLayer;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSnowLayer extends Item {

    public ItemSnowLayer() {
        super( "minecraft:snow_layer", 78 );
    }

    @Override
    public BlockSnowLayer getBlock() {
        return new BlockSnowLayer();
    }
}
