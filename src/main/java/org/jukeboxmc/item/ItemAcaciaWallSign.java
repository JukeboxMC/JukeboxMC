package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockAcaciaWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaWallSign extends Item {

    public ItemAcaciaWallSign() {
        super( "minecraft:acacia_wall_sign", -191 );
    }

    @Override
    public Block getBlock() {
        return new BlockAcaciaWallSign();
    }
}
