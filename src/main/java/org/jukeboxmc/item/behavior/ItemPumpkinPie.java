package org.jukeboxmc.item.behavior;

import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPumpkinPie extends ItemFood {

    public ItemPumpkinPie( Identifier identifier ) {
        super( identifier );
    }

    public ItemPumpkinPie( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public float getSaturation() {
        return 4.8f;
    }

    @Override
    public int getHunger() {
        return 8;
    }
}
