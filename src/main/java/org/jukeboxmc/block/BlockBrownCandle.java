package org.jukeboxmc.block;

import org.jukeboxmc.block.behavior.BlockCandleBehavior;
import org.jukeboxmc.item.ItemBrownCandle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBrownCandle extends BlockCandleBehavior {

    public BlockBrownCandle() {
        super( "minecraft:brown_candle" );
    }

    @Override
    public ItemBrownCandle toItem() {
        return new ItemBrownCandle();
    }

    @Override
    public BlockType getType() {
        return BlockType.BROWN_CANDLE;
    }
}
