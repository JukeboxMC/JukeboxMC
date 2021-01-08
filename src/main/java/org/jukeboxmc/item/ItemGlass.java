package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockGlass;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGlass extends Item {

    public ItemGlass() {
        super( "minecraft:glass", 20 );
    }

    @Override
    public BlockGlass getBlock() {
        return new BlockGlass();
    }
}
