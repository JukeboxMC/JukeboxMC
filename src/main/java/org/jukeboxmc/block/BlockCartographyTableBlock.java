package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCartographyTable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCartographyTableBlock extends Block {

    public BlockCartographyTableBlock() {
        super( "minecraft:cartography_table" );
    }

    @Override
    public ItemCartographyTable toItem() {
        return new ItemCartographyTable();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CARTOGRAPGHY_TABLE;
    }

}
