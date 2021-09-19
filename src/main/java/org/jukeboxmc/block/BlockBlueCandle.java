package org.jukeboxmc.block;

import org.jukeboxmc.block.behavior.BlockCandleBehavior;
import org.jukeboxmc.item.ItemBlueCandle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBlueCandle extends BlockCandleBehavior {

    public BlockBlueCandle() {
        super( "minecraft:blue_candle" );
    }

    @Override
    public ItemBlueCandle toItem() {
        return new ItemBlueCandle();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BLUE_CANDLE;
    }
}
