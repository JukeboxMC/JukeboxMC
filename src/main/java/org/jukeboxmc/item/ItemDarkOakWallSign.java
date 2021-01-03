package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDarkOakWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDarkOakWallSign extends Item {

    public ItemDarkOakWallSign() {
        super( "minecraft:darkoak_wall_sign", -193 );
    }

    @Override
    public Block getBlock() {
        return new BlockDarkOakWallSign();
    }
}
