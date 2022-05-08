package org.jukeboxmc.block;

import org.jukeboxmc.block.behavior.BlockCandleBehavior;
import org.jukeboxmc.item.ItemLightGrayCandle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLightGrayCandle extends BlockCandleBehavior {

    public BlockLightGrayCandle() {
        super( "minecraft:light_gray_candle" );
    }

    @Override
    public ItemLightGrayCandle toItem() {
        return new ItemLightGrayCandle();
    }

    @Override
    public BlockType getType() {
        return BlockType.LIGHT_GRAY;
    }
}
