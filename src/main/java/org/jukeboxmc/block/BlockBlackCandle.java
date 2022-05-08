package org.jukeboxmc.block;

import org.jukeboxmc.block.behavior.BlockCandleBehavior;
import org.jukeboxmc.item.ItemBlackCandle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBlackCandle extends BlockCandleBehavior {

    public BlockBlackCandle() {
        super( "minecraft:black_candle" );
    }

    @Override
    public ItemBlackCandle toItem() {
        return new ItemBlackCandle();
    }

    @Override
    public BlockType getType() {
        return BlockType.BLACK_CANDLE;
    }
}
