package org.jukeboxmc.item.behavior;

import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemMushroomStew extends ItemFood {

    public ItemMushroomStew( Identifier identifier ) {
        super( identifier );
    }

    public ItemMushroomStew( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public float getSaturation() {
        return 7.2f;
    }

    @Override
    public int getHunger() {
        return 6;
    }
}
