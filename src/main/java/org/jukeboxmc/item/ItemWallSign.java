package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWallSign extends Item {

    public ItemWallSign() {
        super( "minecraft:wall_sign", 68 );
    }


    @Override
    public BlockWallSign getBlock() {
        return new BlockWallSign();
    }
}
