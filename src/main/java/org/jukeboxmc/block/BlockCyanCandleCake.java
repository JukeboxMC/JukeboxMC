package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCyanCandleCake;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockCyanCandleCake extends Block {

    public BlockCyanCandleCake() {
        super( "minecraft:cyan_candle_cake" );
    }

    @Override
    public ItemCyanCandleCake toItem() {
        return new ItemCyanCandleCake();
    }

    @Override
    public BlockType getType() {
        return BlockType.CYAN_CANDLE_CAKE;
    }
}