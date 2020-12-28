package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAir extends Item {

    public ItemAir() {
        super( "minecraft:air", -158 );
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }
}
