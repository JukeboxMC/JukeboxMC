package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWoddenButton extends Item {

    public ItemWoddenButton() {
        super( "wooden_button", 134 );
    }

    @Override
    public int getMaxAmount() {
        return 64;
    }
}
