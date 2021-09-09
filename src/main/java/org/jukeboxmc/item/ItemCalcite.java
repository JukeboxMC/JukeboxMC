package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCalcite;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCalcite extends Item{

    public ItemCalcite() {
        super( "minecraft:calcite" );
    }

    @Override
    public BlockCalcite getBlock() {
        return new BlockCalcite();
    }
}
