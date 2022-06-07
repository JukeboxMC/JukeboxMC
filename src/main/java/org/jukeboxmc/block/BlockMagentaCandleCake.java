package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemMagentaCandleCake;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMagentaCandleCake extends Block {

    public BlockMagentaCandleCake() {
        super( "minecraft:magenta_candle_cake" );
    }

    @Override
    public ItemMagentaCandleCake toItem() {
        return new ItemMagentaCandleCake();
    }

    @Override
    public BlockType getType() {
        return BlockType.MAGENTA_CANDLE_CAKE;
    }
}