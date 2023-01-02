package org.jukeboxmc.item.behavior;

import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemApple extends ItemFood {

    public ItemApple( Identifier identifier ) {
        super( identifier );
    }

    public ItemApple( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public float getSaturation() {
        return 2.4f;
    }

    @Override
    public int getHunger() {
        return 4;
    }
}
