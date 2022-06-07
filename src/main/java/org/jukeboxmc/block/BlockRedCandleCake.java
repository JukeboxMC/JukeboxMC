package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemRedCandleCake;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockRedCandleCake extends Block {

    public BlockRedCandleCake() {
        super( "minecraft:red_candle_cake" );
    }

    @Override
    public ItemRedCandleCake toItem() {
        return new ItemRedCandleCake();
    }

    @Override
    public BlockType getType() {
        return BlockType.RED_CANDLE_CAKE;
    }
}