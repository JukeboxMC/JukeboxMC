package org.jukeboxmc.item.behavior;

import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCookedPorkchop extends ItemFood {

    public ItemCookedPorkchop( Identifier identifier ) {
        super( identifier );
    }

    public ItemCookedPorkchop( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public float getSaturation() {
        return 12.8f;
    }

    @Override
    public int getHunger() {
        return 8;
    }
}
