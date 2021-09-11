package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCartographyTable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCartographyTable extends Item {

    public ItemCartographyTable() {
        super ( "minecraft:cartography_table" );
    }

    @Override
    public BlockCartographyTable getBlock() {
        return new BlockCartographyTable();
    }
}
