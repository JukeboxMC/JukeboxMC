package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDarkOakFenceGate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDarkOakFenceGate extends BlockFenceGate {

    public BlockDarkOakFenceGate() {
        super( "minecraft:dark_oak_fence_gate" );
    }

    @Override
    public ItemDarkOakFenceGate toItem() {
        return new ItemDarkOakFenceGate();
    }

    @Override
    public BlockType getType() {
        return BlockType.DARK_OAK_FENCE_GATE;
    }

}
