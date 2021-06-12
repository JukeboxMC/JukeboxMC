package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSpruceFenceGate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSpruceFenceGate extends Item {

    public ItemSpruceFenceGate() {
        super ( "minecraft:spruce_fence_gate" );
    }

    @Override
    public BlockSpruceFenceGate getBlock() {
        return new BlockSpruceFenceGate();
    }
}
