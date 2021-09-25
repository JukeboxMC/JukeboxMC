package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemMinecart extends Item {

    public ItemMinecart() {
        super ( "minecraft:minecart" );
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }
}
