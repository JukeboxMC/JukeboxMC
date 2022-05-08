package org.jukeboxmc.block;

import org.jukeboxmc.block.behavior.BlockCandleBehavior;
import org.jukeboxmc.item.ItemLimeCandle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLimeCandle extends BlockCandleBehavior {

    public BlockLimeCandle() {
        super( "minecraft:lime_candle" );
    }

    @Override
    public ItemLimeCandle toItem() {
        return new ItemLimeCandle();
    }

    @Override
    public BlockType getType() {
        return BlockType.LIME_CANDLE;
    }
}
