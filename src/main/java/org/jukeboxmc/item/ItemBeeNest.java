package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBeeNest;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBeeNest extends Item {

    public ItemBeeNest() {
        super( "minecraft:bee_nest", -218 );
    }

    @Override
    public BlockBeeNest getBlock() {
        return new BlockBeeNest();
    }
}
