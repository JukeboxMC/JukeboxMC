package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWhiteCandleCake;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockWhiteCandleCake extends Block {

    public BlockWhiteCandleCake() {
        super( "minecraft:white_candle_cake" );
    }

    @Override
    public ItemWhiteCandleCake toItem() {
        return new ItemWhiteCandleCake();
    }

    @Override
    public BlockType getType() {
        return BlockType.WHITE_CANDLE_CAKE;
    }
}