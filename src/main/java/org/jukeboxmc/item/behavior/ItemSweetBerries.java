package org.jukeboxmc.item.behavior;

import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSweetBerries extends ItemFood{

    public ItemSweetBerries( Identifier identifier ) {
        super( identifier );
    }

    public ItemSweetBerries( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public float getSaturation() {
        return 1.2f;
    }

    @Override
    public int getHunger() {
        return 2;
    }
}
