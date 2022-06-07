package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBlueCandleCake;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockBlueCandleCake extends Block {

    public BlockBlueCandleCake() {
        super( "minecraft:blue_candle_cake" );
    }

    @Override
    public ItemBlueCandleCake toItem() {
        return new ItemBlueCandleCake();
    }

    @Override
    public BlockType getType() {
        return BlockType.BLUE_CANDLE_CAKE;
    }
}