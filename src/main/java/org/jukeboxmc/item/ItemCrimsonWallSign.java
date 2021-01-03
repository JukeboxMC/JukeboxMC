package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCrimsonWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonWallSign extends Item {

    public ItemCrimsonWallSign() {
        super( "minecraft:crimson_wall_sign", -252 );
    }

    @Override
    public Block getBlock() {
        return new BlockCrimsonWallSign();
    }
}
