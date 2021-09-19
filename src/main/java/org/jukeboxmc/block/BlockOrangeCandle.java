package org.jukeboxmc.block;

import org.jukeboxmc.block.behavior.BlockCandleBehavior;
import org.jukeboxmc.item.ItemOrangeCandle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockOrangeCandle extends BlockCandleBehavior {

    public BlockOrangeCandle() {
        super( "minecraft:orange_candle" );
    }

    @Override
    public ItemOrangeCandle toItem() {
        return new ItemOrangeCandle();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ORANGE_CANDLE;
    }
}
