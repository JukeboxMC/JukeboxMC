package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRedNetherBrick;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedNetherBrick extends Item {

    public ItemRedNetherBrick() {
        super ( "minecraft:red_nether_brick" );
    }

    @Override
    public BlockRedNetherBrick getBlock() {
        return new BlockRedNetherBrick();
    }
}
