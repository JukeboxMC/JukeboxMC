package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemYellowCandleCake;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockYellowCandleCake extends Block {

    public BlockYellowCandleCake() {
        super( "minecraft:yellow_candle_cake" );
    }

    @Override
    public ItemYellowCandleCake toItem() {
        return new ItemYellowCandleCake();
    }

    @Override
    public BlockType getType() {
        return BlockType.YELLOW_CANDLE_CAKE;
    }
}