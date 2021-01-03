package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockChemistryTable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemChemistryTable extends Item {

    public ItemChemistryTable() {
        super( "minecraft:chemistry_table", 238 );
    }

    @Override
    public Block getBlock() {
        return new BlockChemistryTable();
    }
}
