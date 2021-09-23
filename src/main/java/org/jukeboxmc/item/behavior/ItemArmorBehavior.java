package org.jukeboxmc.item.behavior;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.type.ArmorTierType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class ItemArmorBehavior extends Item {

    public ItemArmorBehavior( String identifier ) {
        super( identifier );
    }

    public abstract ArmorTierType getArmorTierType();

    public abstract int getArmorPoints();
}
