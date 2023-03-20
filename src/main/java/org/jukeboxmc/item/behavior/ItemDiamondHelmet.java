package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
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
public class ItemDiamondHelmet extends ItemArmor implements Durability {

    public ItemDiamondHelmet( ItemType itemType ) {
        super( itemType );
    }

    public ItemDiamondHelmet( Identifier identifier ) {
        super( identifier );
    }

    @Override
    public boolean useInAir(@NotNull Player player, Vector clickVector ) {
        Item oldItem = player.getArmorInventory().getHelmet();
        player.getArmorInventory().setHelmet( this );
        player.getInventory().setItemInHand( oldItem );
        return super.useInAir( player, clickVector );
    }

    @Override
    public @NotNull ArmorTierType getArmorTierType() {
        return ArmorTierType.DIAMOND;
    }

    @Override
    public int getArmorPoints() {
        return 3;
    }

    @Override
    public void playEquipSound(@NotNull Player player ) {
        player.playSound( Sound.ARMOR_EQUIP_DIAMOND );
    }

    @Override
    public int getMaxDurability() {
        return 368;
    }
}
