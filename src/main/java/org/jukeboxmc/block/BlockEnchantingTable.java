package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemEnchantingTable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEnchantingTable extends Block {

    public BlockEnchantingTable() {
        super( "minecraft:enchanting_table" );
    }

    @Override
    public ItemEnchantingTable toItem() {
        return new ItemEnchantingTable();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ENCHANTING_TABLE;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}
