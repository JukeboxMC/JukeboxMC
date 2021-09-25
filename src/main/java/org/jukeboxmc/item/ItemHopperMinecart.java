package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemHopperMinecart extends Item {

    public ItemHopperMinecart() {
        super ( "minecraft:hopper_minecart" );
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }
}
