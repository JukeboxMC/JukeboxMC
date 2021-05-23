package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockFletchingTable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFletchingTable extends Item {

    public ItemFletchingTable() {
        super( -201 );
    }

    @Override
    public BlockFletchingTable getBlock() {
        return new BlockFletchingTable();
    }
}
