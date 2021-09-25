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
public class ItemDiamondPickaxe extends Item implements Durability {

    public ItemDiamondPickaxe() {
        super ( "minecraft:diamond_pickaxe" );
    }

    @Override
    public void addToHand( Player player ) {
        Attribute attribute = player.getAttribute( AttributeType.ATTACK_DAMAGE );
        attribute.setCurrentValue( 5 );
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
        return ItemTierType.DIAMOND;
    }

    @Override
    public int getMaxDurability() {
        return 1561;
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }
}
