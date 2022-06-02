package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemGrayCandleCake;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockGrayCandleCake extends Block {

    public BlockGrayCandleCake() {
        super( "minecraft:gray_candle_cake" );
    }

    @Override
    public ItemGrayCandleCake toItem() {
        return new ItemGrayCandleCake();
    }

    @Override
    public BlockType getType() {
        return BlockType.GRAY_CANDLE_CAKE;
    }
}