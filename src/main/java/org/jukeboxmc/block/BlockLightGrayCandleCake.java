package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemLightGrayCandleCake;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockLightGrayCandleCake extends Block {

    public BlockLightGrayCandleCake() {
        super( "minecraft:light_gray_candle_cake" );
    }

    @Override
    public ItemLightGrayCandleCake toItem() {
        return new ItemLightGrayCandleCake();
    }

    @Override
    public BlockType getType() {
        return BlockType.LIGHT_GRAY_CANDLE_CAKE;
    }
}