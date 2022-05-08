package org.jukeboxmc.block;

import org.jukeboxmc.block.behavior.BlockCandleBehavior;
import org.jukeboxmc.item.ItemPinkCandle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPinkCandle extends BlockCandleBehavior {

    public BlockPinkCandle() {
        super( "minecraft:pink_candle" );
    }

    @Override
    public ItemPinkCandle toItem() {
        return new ItemPinkCandle();
    }

    @Override
    public BlockType getType() {
        return BlockType.PINK_CANDLE;
    }
}
