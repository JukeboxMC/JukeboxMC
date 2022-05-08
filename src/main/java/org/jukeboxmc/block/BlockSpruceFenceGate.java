package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemSpruceFenceGate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSpruceFenceGate extends BlockFenceGate {

    public BlockSpruceFenceGate() {
        super( "minecraft:spruce_fence_gate" );
    }

    @Override
    public ItemSpruceFenceGate toItem() {
        return new ItemSpruceFenceGate();
    }

    @Override
    public BlockType getType() {
        return BlockType.SPRUCE_FENCE_GATE;
    }

}
