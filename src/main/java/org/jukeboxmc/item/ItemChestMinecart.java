package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemChestMinecart extends Item {

    public ItemChestMinecart() {
        super ( "minecraft:chest_minecart" );
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }

}
