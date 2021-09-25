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

    @Override
    public int getMaxAmount() {
        return 1;
    }

    public abstract ArmorTierType getArmorTierType();

    public abstract int getArmorPoints();
}
