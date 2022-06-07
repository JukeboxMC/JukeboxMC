package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemLimeCandleCake;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockLimeCandleCake extends Block {

    public BlockLimeCandleCake() {
        super( "minecraft:lime_candle_cake" );
    }

    @Override
    public ItemLimeCandleCake toItem() {
        return new ItemLimeCandleCake();
    }

    @Override
    public BlockType getType() {
        return BlockType.LIME_CANDLE_CAKE;
    }
}