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
public class ItemIronHelmet extends ItemArmorBehavior implements Durability {

    public ItemIronHelmet() {
        super ( "minecraft:iron_helmet" );
    }

    @Override
    public boolean useInAir( Player player, Vector clickVector ) {
        Item oldItem = player.getArmorInventory().getHelmet();
        player.getArmorInventory().setHelmet( this );
        player.getInventory().setItemInHand( oldItem );
        return true;
    }

    @Override
    public ArmorTierType getArmorTierType() {
        return ArmorTierType.IRON;
    }

    @Override
    public int getArmorPoints() {
        return 2;
    }

    @Override
    public int getMaxDurability() {
        return 165;
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }
}
