package org.jukeboxmc.block;

import org.jukeboxmc.block.behavior.BlockCandleBehavior;
import org.jukeboxmc.item.ItemCandle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCandle extends BlockCandleBehavior {

    public BlockCandle() {
        super( "minecraft:candle" );
    }

    @Override
    public ItemCandle toItem() {
        return new ItemCandle();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CANDLE;
    }
}
