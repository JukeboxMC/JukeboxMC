package org.jukeboxmc.block;

import org.jukeboxmc.block.behavior.BlockCandleBehavior;
import org.jukeboxmc.item.ItemPurpleCandle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPurpleCandle extends BlockCandleBehavior {

    public BlockPurpleCandle() {
        super( "minecraft:purple_candle" );
    }

    @Override
    public ItemPurpleCandle toItem() {
        return new ItemPurpleCandle();
    }

    @Override
    public BlockType getType() {
        return BlockType.PURPLE_CANDLE;
    }
}
