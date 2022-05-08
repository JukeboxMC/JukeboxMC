package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemJungleFenceGate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockJungleFenceGate extends BlockFenceGate {

    public BlockJungleFenceGate() {
        super( "minecraft:jungle_fence_gate" );
    }

    @Override
    public ItemJungleFenceGate toItem() {
        return new ItemJungleFenceGate();
    }

    @Override
    public BlockType getType() {
        return BlockType.JUNGLE_FENCE_GATE;
    }

}
