package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemPurpleCandleCake;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockPurpleCandleCake extends Block {

    public BlockPurpleCandleCake() {
        super( "minecraft:purple_candle_cake" );
    }

    @Override
    public ItemPurpleCandleCake toItem() {
        return new ItemPurpleCandleCake();
    }

    @Override
    public BlockType getType() {
        return BlockType.PURPLE_CANDLE_CAKE;
    }
}