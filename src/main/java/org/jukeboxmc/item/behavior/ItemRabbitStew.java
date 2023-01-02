package org.jukeboxmc.item.behavior;

import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRabbitStew extends ItemFood {

    public ItemRabbitStew( Identifier identifier ) {
        super( identifier );
    }

    public ItemRabbitStew( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public float getSaturation() {
        return 12;
    }

    @Override
    public int getHunger() {
        return 10;
    }
}
