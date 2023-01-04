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
public class ItemGoldenBoots extends ItemArmor implements Durability {

    public ItemGoldenBoots( ItemType itemType ) {
        super( itemType );
    }

    public ItemGoldenBoots( Identifier identifier ) {
        super( identifier );
    }

    @Override
    public boolean useInAir( Player player, Vector clickVector ) {
        Item oldItem = player.getArmorInventory().getBoots();
        player.getArmorInventory().setBoots( this );
        player.getInventory().setItemInHand( oldItem );
        return super.useInAir( player, clickVector );
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
    public void playEquipSound( Player player ) {
        player.playSound( Sound.ARMOR_EQUIP_GOLD );
    }

    @Override
    public int getMaxDurability() {
        return 91;
    }
}
