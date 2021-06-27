package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockAzaleaLeavesFlowered;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAzaleaLeavesFlowered extends Item {

    public ItemAzaleaLeavesFlowered() {
        super( "minecraft:azalea_leaves_flowered" );
    }

    @Override
    public BlockAzaleaLeavesFlowered getBlock() {
        return new BlockAzaleaLeavesFlowered();
    }
}
