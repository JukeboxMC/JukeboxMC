package org.jukeboxmc.item.behavior;

import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCarrot extends ItemFood {

    public ItemCarrot( Identifier identifier ) {
        super( identifier );
    }

    public ItemCarrot( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public float getSaturation() {
        return 3.6f;
    }

    @Override
    public int getHunger() {
        return 3;
    }
}
