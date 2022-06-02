package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCandleCake;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockCandleCake extends Block {

    public BlockCandleCake() {
        super( "minecraft:candle_cake" );
    }

    @Override
    public ItemCandleCake toItem() {
        return new ItemCandleCake();
    }

    @Override
    public BlockType getType() {
        return BlockType.CANDLE_CAKE;
    }
}