package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPoweredRepeater;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPoweredRepeater extends Item {

    public ItemPoweredRepeater() {
        super ( "minecraft:powered_repeater" );
    }

    @Override
    public BlockPoweredRepeater getBlock() {
        return new BlockPoweredRepeater();
    }
}
