package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStone extends Item {

    public ItemStone() {
        super( "minecraft:stone", 1 );
    }

    @Override
    public int getMaxAmount() {
        return 64;
    }
}
