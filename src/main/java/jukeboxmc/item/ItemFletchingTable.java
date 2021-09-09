package jukeboxmc.item;

import org.jukeboxmc.block.BlockFletchingTable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFletchingTable extends Item {

    public ItemFletchingTable() {
        super ( "minecraft:fletching_table" );
    }

    @Override
    public BlockFletchingTable getBlock() {
        return new BlockFletchingTable();
    }
}
