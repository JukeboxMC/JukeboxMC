package jukeboxmc.item;

import org.jukeboxmc.block.BlockChemistryTable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemChemistryTable extends Item {

    public ItemChemistryTable() {
        super( "minecraft:chemistry_table" );
    }

    @Override
    public BlockChemistryTable getBlock() {
        return new BlockChemistryTable();
    }
}
