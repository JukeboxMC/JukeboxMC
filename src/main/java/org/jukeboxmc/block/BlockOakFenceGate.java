package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemFenceGate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockOakFenceGate extends BlockFenceGate {

    public BlockOakFenceGate() {
        super( "minecraft:fence_gate" );
    }

    @Override
    public ItemFenceGate toItem() {
        return new ItemFenceGate();
    }

    @Override
    public BlockType getType() {
        return BlockType.FENCE_GATE;
    }

}
