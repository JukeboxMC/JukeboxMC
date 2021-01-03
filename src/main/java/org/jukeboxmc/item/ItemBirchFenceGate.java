package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBirchFenceGate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBirchFenceGate extends Item {

    public ItemBirchFenceGate() {
        super( "minecraft:birch_fence_gate", 184 );
    }

    @Override
    public Block getBlock() {
        return new BlockBirchFenceGate();
    }
}
