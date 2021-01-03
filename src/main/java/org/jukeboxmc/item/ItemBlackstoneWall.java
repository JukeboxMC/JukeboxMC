package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBlackstoneWall;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBlackstoneWall extends Item {

    public ItemBlackstoneWall() {
        super( "minecraft:blackstone_wall", -277 );
    }

    @Override
    public Block getBlock() {
        return new BlockBlackstoneWall();
    }
}
