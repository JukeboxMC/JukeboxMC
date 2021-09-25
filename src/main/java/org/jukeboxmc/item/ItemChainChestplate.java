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
public class ItemChainChestplate extends ItemArmorBehavior implements Durability {

    public ItemChainChestplate() {
        super ( "minecraft:chainmail_chestplate" );
    }

    @Override
    public boolean useInAir( Player player, Vector clickVector ) {
        Item oldItem = player.getArmorInventory().getChestplate();
        player.getArmorInventory().setChestplate( this );
        player.getInventory().setItemInHand( oldItem );
        return true;
    }

    @Override
    public ArmorTierType getArmorTierType() {
        return ArmorTierType.CHAIN;
    }

    @Override
    public int getArmorPoints() {
        return 5;
    }

    @Override
    public int getMaxDurability() {
        return 240;
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }
}
