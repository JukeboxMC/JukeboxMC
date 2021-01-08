package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockNoteblock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNoteblock extends Item {

    public ItemNoteblock() {
        super( "minecraft:noteblock", 25 );
    }

    @Override
    public BlockNoteblock getBlock() {
        return new BlockNoteblock();
    }
}
