package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemOrangeCandleCake;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockOrangeCandleCake extends Block {

    public BlockOrangeCandleCake() {
        super( "minecraft:orange_candle_cake" );
    }

    @Override
    public ItemOrangeCandleCake toItem() {
        return new ItemOrangeCandleCake();
    }

    @Override
    public BlockType getType() {
        return BlockType.ORANGE_CANDLE_CAKE;
    }
}