package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSmithingTable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSmithingTable extends Item {

    public ItemSmithingTable() {
        super( "minecraft:smithing_table", -202 );
    }

    @Override
    public BlockSmithingTable getBlock() {
        return new BlockSmithingTable();
    }
}
