package org.jukeboxmc.block;

import org.jukeboxmc.block.behavior.BlockCandleBehavior;
import org.jukeboxmc.item.ItemGreenCandle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGreenCandle extends BlockCandleBehavior {

    public BlockGreenCandle() {
        super( "minecraft:green_candle" );
    }

    @Override
    public ItemGreenCandle toItem() {
        return new ItemGreenCandle();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GREEN_CANDLE;
    }
}
