package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLectern;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLectern extends Item {

    public ItemLectern() {
        super ( "minecraft:lectern" );
    }

    @Override
    public BlockLectern getBlock() {
        return new BlockLectern();
    }
}
