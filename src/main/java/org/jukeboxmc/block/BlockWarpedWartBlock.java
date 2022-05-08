package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWarpedWartBlock;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWarpedWartBlock extends Block {

    public BlockWarpedWartBlock() {
        super( "minecraft:warped_wart_block" );
    }

    @Override
    public ItemWarpedWartBlock toItem() {
        return new ItemWarpedWartBlock();
    }

    @Override
    public BlockType getType() {
        return BlockType.WARPED_WART_BLOCK;
    }

    @Override
    public double getHardness() {
        return 1;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.HOE;
    }

}
