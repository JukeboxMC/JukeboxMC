package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCandle;
import org.jukeboxmc.item.behavior.ItemCandleBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCandle extends ItemCandleBehavior {

    public ItemCandle() {
        super( "minecraft:candle" );
    }

    @Override
    public BlockCandle getBlock() {
        return new BlockCandle();
    }

}
