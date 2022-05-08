package org.jukeboxmc.block;

import org.jukeboxmc.block.behavior.BlockCandleBehavior;
import org.jukeboxmc.item.ItemYellowCandle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockYellowCandle extends BlockCandleBehavior {

    public BlockYellowCandle() {
        super( "minecraft:yellow_candle" );
    }

    @Override
    public ItemYellowCandle toItem() {
        return new ItemYellowCandle();
    }

    @Override
    public BlockType getType() {
        return BlockType.YELLOW_CANDLE;
    }
}
