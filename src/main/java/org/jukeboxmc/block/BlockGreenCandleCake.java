package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemGreenCandleCake;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockGreenCandleCake extends Block {

    public BlockGreenCandleCake() {
        super( "minecraft:green_candle_cake" );
    }

    @Override
    public ItemGreenCandleCake toItem() {
        return new ItemGreenCandleCake();
    }

    @Override
    public BlockType getType() {
        return BlockType.GREEN_CANDLE_CAKE;
    }
}