package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemNoteblock;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockNoteblock extends Block {

    public BlockNoteblock() {
        super( "minecraft:noteblock" );
    }

    @Override
    public ItemNoteblock toItem() {
        return new ItemNoteblock();
    }

    @Override
    public BlockType getType() {
        return BlockType.NOTEBLOCK;
    }

    @Override
    public double getHardness() {
        return 0.8;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.AXE;
    }

}
