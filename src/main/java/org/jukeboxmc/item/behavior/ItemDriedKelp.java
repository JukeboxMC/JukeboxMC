package org.jukeboxmc.item.behavior;

import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDriedKelp extends ItemFood {

    public ItemDriedKelp( Identifier identifier ) {
        super( identifier );
    }

    public ItemDriedKelp( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public float getSaturation() {
        return 0.2f;
    }

    @Override
    public int getHunger() {
        return 1;
    }
}
