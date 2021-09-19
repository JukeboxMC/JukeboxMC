package org.jukeboxmc.block;

import org.jukeboxmc.block.behavior.BlockCandleBehavior;
import org.jukeboxmc.item.ItemRedCandle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRedCandle extends BlockCandleBehavior {

    public BlockRedCandle( ) {
        super( "minecraft:red_candle" );
    }

    @Override
    public ItemRedCandle toItem() {
        return new ItemRedCandle();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.RED_CANDLE;
    }
}
