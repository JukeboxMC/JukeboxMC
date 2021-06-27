package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemAzaleaLeavesFlowered;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAzaleaLeavesFlowered extends Block{

    public BlockAzaleaLeavesFlowered() {
        super( "minecraft:azalea_leaves_flowered" );
    }

    @Override
    public ItemAzaleaLeavesFlowered toItem() {
        return new ItemAzaleaLeavesFlowered();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.AZALEA_LEAVES_FLOWERED;
    }
}
