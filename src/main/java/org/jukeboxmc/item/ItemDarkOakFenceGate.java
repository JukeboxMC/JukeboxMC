package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDarkOakFenceGate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDarkOakFenceGate extends Item {

    public ItemDarkOakFenceGate() {
        super( "minecraft:dark_oak_fence_gate", 186 );
    }

    @Override
    public Block getBlock() {
        return new BlockDarkOakFenceGate();
    }
}
