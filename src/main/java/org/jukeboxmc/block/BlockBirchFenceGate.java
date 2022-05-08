package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBirchFenceGate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBirchFenceGate extends BlockFenceGate {

    public BlockBirchFenceGate() {
        super( "minecraft:birch_fence_gate" );
    }

    @Override
    public ItemBirchFenceGate toItem() {
        return new ItemBirchFenceGate();
    }

    @Override
    public BlockType getType() {
        return BlockType.BIRCH_FENCE_GATE;
    }

}
