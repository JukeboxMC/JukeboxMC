package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemArmorBehavior;
import org.jukeboxmc.item.type.ArmorTierType;
import org.jukeboxmc.item.type.Durability;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGoldenBoots extends ItemArmorBehavior implements Durability {

    public ItemGoldenBoots() {
        super ( "minecraft:golden_boots" );
    }

    @Override
    public boolean useInAir( Player player, Vector clickVector ) {
        Item oldItem = player.getArmorInventory().getBoots();
        player.getArmorInventory().setBoots( this );
        player.getInventory().setItemInHand( oldItem );
        return true;
    }

    @Override
    public ArmorTierType getArmorTierType() {
        return ArmorTierType.GOLD;
    }

    @Override
    public int getArmorPoints() {
        return 1;
    }

    @Override
    public int getMaxDurability() {
        return 91;
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }
}
