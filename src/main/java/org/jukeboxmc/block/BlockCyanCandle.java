package org.jukeboxmc.block;

import org.jukeboxmc.block.behavior.BlockCandleBehavior;
import org.jukeboxmc.item.ItemCyanCandle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCyanCandle extends BlockCandleBehavior {

    public BlockCyanCandle() {
        super( "minecraft:cyan_candle" );
    }

    @Override
    public ItemCyanCandle toItem() {
        return new ItemCyanCandle();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CYAN_CANDLE;
    }
}
