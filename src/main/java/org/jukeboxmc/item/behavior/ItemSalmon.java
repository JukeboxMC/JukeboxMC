package org.jukeboxmc.item.behavior;

import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSalmon extends ItemFood {

    public ItemSalmon( Identifier identifier ) {
        super( identifier );
    }

    public ItemSalmon( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public float getSaturation() {
        return 0.4f;
    }

    @Override
    public int getHunger() {
        return 2;
    }
}
