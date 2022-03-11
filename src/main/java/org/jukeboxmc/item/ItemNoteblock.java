package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockNoteblock;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNoteblock extends Item implements Burnable {

    public ItemNoteblock() {
        super ( "minecraft:noteblock" );
    }

    @Override
    public BlockNoteblock getBlock() {
        return new BlockNoteblock();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
