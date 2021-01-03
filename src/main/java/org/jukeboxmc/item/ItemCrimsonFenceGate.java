package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCrimsonFenceGate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonFenceGate extends Item {

    public ItemCrimsonFenceGate() {
        super( "minecraft:crimson_fence_gate", -258 );
    }

    @Override
    public Block getBlock() {
        return new BlockCrimsonFenceGate();
    }
}
