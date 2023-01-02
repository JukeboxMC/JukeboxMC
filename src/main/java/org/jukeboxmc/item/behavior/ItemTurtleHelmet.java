package org.jukeboxmc.item.behavior;

import org.jukeboxmc.item.ArmorTierType;
import org.jukeboxmc.item.Durability;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemTurtleHelmet extends ItemArmor implements Durability {

    public ItemTurtleHelmet( ItemType itemType ) {
        super( itemType );
    }

    public ItemTurtleHelmet( Identifier identifier ) {
        super( identifier );
    }

    @Override
    public ArmorTierType getArmorTierType() {
        return ArmorTierType.TURTLE;
    }

    @Override
    public int getArmorPoints() {
        return 2;
    }

    @Override
    public int getMaxDurability() {
        return 275;
    }
}
