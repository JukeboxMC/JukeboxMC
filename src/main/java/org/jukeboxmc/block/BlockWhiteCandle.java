package org.jukeboxmc.block;

import org.jukeboxmc.block.behavior.BlockCandleBehavior;
import org.jukeboxmc.item.ItemWhiteCandle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWhiteCandle extends BlockCandleBehavior {

    public BlockWhiteCandle() {
        super( "minecraft:white_candle" );
    }

    @Override
    public ItemWhiteCandle toItem() {
        return new ItemWhiteCandle();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WHITE_CANDLE;
    }
}
