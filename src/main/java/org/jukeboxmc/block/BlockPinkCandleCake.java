package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemPinkCandleCake;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockPinkCandleCake extends Block {

    public BlockPinkCandleCake() {
        super( "minecraft:pink_candle_cake" );
    }

    @Override
    public ItemPinkCandleCake toItem() {
        return new ItemPinkCandleCake();
    }

    @Override
    public BlockType getType() {
        return BlockType.PINK_CANDLE_CAKE;
    }
}