package org.jukeboxmc.item.behavior;

import org.jukeboxmc.item.ArmorTierType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class ItemArmor extends Item {

    public ItemArmor( ItemType itemType ) {
        super( itemType );
    }

    public ItemArmor( Identifier identifier ) {
        super( identifier );
    }

    @Override
    public boolean useInAir( Player player, Vector clickVector ) {
        this.playEquipSound( player );
        return true;
    }

    public abstract ArmorTierType getArmorTierType();

    public abstract int getArmorPoints();

    public abstract void playEquipSound( Player player );
}
