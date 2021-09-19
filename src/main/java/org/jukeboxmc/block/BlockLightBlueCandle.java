package org.jukeboxmc.block;

import org.jukeboxmc.block.behavior.BlockCandleBehavior;
import org.jukeboxmc.item.ItemLightBlueCandle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLightBlueCandle extends BlockCandleBehavior {

    public BlockLightBlueCandle() {
        super( "minecraft:light_blue_candle" );
    }

    @Override
    public ItemLightBlueCandle toItem() {
        return new ItemLightBlueCandle();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LIGHT_BLUE_CANDLE;
    }
}
