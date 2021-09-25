package org.jukeboxmc.item;

import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.entity.attribute.AttributeType;
import org.jukeboxmc.item.type.Durability;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGoldenPickaxe extends Item implements Durability {

    public ItemGoldenPickaxe() {
        super ( "minecraft:golden_pickaxe" );
    }

    @Override
    public void addToHand( Player player ) {
        Attribute attribute = player.getAttribute( AttributeType.ATTACK_DAMAGE );
        attribute.setCurrentValue( 2 );
    }

    @Override
    public void removeFromHand( Player player ) {
        Attribute attribute = player.getAttribute( AttributeType.ATTACK_DAMAGE );
        attribute.setCurrentValue( attribute.getMinValue() );
    }

    @Override
    public ItemToolType getItemToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public ItemTierType getTierType() {
        return ItemTierType.GOLD;
    }

    @Override
    public int getMaxDurability() {
        return 32;
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }
}
