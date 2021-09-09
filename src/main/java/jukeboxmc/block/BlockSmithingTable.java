package jukeboxmc.block;

import org.jukeboxmc.item.ItemSmithingTable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSmithingTable extends Block {

    public BlockSmithingTable() {
        super( "minecraft:smithing_table" );
    }

    @Override
    public ItemSmithingTable toItem() {
        return new ItemSmithingTable();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SMITHING_TABLE;
    }

}
