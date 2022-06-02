package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemLightBlueCandleCake;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockLightBlueCandleCake extends Block {

    public BlockLightBlueCandleCake() {
        super( "minecraft:light_blue_candle_cake" );
    }

    @Override
    public ItemLightBlueCandleCake toItem() {
        return new ItemLightBlueCandleCake();
    }

    @Override
    public BlockType getType() {
        return BlockType.LIGHT_BLUE_CANDLE_CAKE;
    }
}