package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDeepslateBrickWall;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDeepslateBrickWall extends Item {

    public ItemDeepslateBrickWall() {
        super( "minecraft:deepslate_brick_wall" );
    }

    @Override
    public BlockDeepslateBrickWall getBlock() {
        return new BlockDeepslateBrickWall();
    }
}
