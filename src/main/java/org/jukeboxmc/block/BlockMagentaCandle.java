package org.jukeboxmc.block;

import org.jukeboxmc.block.behavior.BlockCandleBehavior;
import org.jukeboxmc.item.ItemMagentaCandle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMagentaCandle extends BlockCandleBehavior {

    public BlockMagentaCandle() {
        super( "minecraft:magenta_candle" );
    }

    @Override
    public ItemMagentaCandle toItem() {
        return new ItemMagentaCandle();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.MAGENTA_CANDLE;
    }
}
