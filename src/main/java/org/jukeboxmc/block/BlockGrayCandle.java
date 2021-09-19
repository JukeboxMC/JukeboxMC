package org.jukeboxmc.block;

import org.jukeboxmc.block.behavior.BlockCandleBehavior;
import org.jukeboxmc.item.ItemGrayCandle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGrayCandle extends BlockCandleBehavior {

    public BlockGrayCandle() {
        super( "minecraft:gray_candle" );
    }

    @Override
    public ItemGrayCandle toItem() {
        return new ItemGrayCandle();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GRAY_CANDLE;
    }
}
