package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBrownCandleCake;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockBrownCandleCake extends Block {

    public BlockBrownCandleCake() {
        super( "minecraft:brown_candle_cake" );
    }

    @Override
    public ItemBrownCandleCake toItem() {
        return new ItemBrownCandleCake();
    }

    @Override
    public BlockType getType() {
        return BlockType.BROWN_CANDLE_CAKE;
    }
}