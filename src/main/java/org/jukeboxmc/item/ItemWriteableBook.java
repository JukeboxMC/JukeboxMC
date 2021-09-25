package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWriteableBook extends Item {

    public ItemWriteableBook() {
        super ( "minecraft:writable_book" );
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }

}
