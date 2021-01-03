package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCrimsonFence;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonFence extends Item {

    public ItemCrimsonFence() {
        super( "minecraft:crimson_fence", -256 );
    }

    @Override
    public Block getBlock() {
        return new BlockCrimsonFence();
    }
}
