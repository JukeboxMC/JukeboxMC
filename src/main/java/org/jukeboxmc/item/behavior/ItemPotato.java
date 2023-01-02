package org.jukeboxmc.item.behavior;

import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPotato extends ItemFood{

    public ItemPotato( Identifier identifier ) {
        super( identifier );
    }

    public ItemPotato( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public float getSaturation() {
        return 0.6f;
    }

    @Override
    public int getHunger() {
        return 1;
    }
}
