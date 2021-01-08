package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSnow;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSnow extends Item {

    public ItemSnow() {
        super( "minecraft:snow", 80 );
    }

    @Override
    public BlockSnow getBlock() {
        return new BlockSnow();
    }
}
