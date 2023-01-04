package org.jukeboxmc.item.behavior;

import org.jukeboxmc.item.ArmorTierType;
import org.jukeboxmc.item.Durability;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.Sound;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemChainLeggings extends ItemArmor implements Durability {

    public ItemChainLeggings( ItemType itemType ) {
        super( itemType );
    }

    public ItemChainLeggings( Identifier identifier ) {
        super( identifier );
    }

    @Override
    public boolean useInAir( Player player, Vector clickVector ) {
        Item oldItem = player.getArmorInventory().getLeggings();
        player.getArmorInventory().setLeggings( this );
        player.getInventory().setItemInHand( oldItem );
        return super.useInAir( player, clickVector );
    }

    @Override
    public ArmorTierType getArmorTierType() {
        return ArmorTierType.CHAIN;
    }

    @Override
    public int getArmorPoints() {
        return 4;
    }

    @Override
    public void playEquipSound( Player player ) {
        player.playSound( Sound.ARMOR_EQUIP_CHAIN );
    }

    @Override
    public int getMaxDurability() {
        return 225;
    }
}
