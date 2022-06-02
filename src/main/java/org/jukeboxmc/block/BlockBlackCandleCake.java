package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBlackCandleCake;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockBlackCandleCake extends Block {

    public BlockBlackCandleCake() {
        super( "minecraft:black_candle_cake" );
    }

    @Override
    public ItemBlackCandleCake toItem() {
        return new ItemBlackCandleCake();
    }

    @Override
    public BlockType getType() {
        return BlockType.BLACK_CANDLE_CAKE;
    }
}