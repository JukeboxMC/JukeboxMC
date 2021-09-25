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
public class ItemStoneAxe extends Item implements Durability {

    public ItemStoneAxe() {
        super ( "minecraft:stone_axe" );
    }

    @Override
    public void addToHand( Player player ) {
        Attribute attribute = player.getAttribute( AttributeType.ATTACK_DAMAGE );
        attribute.setCurrentValue( 4 );
    }

    @Override
    public void removeFromHand( Player player ) {
        Attribute attribute = player.getAttribute( AttributeType.ATTACK_DAMAGE );
        attribute.setCurrentValue( attribute.getMinValue() );
    }

    @Override
    public ItemToolType getItemToolType() {
        return ItemToolType.AXE;
    }

    @Override
    public ItemTierType getTierType() {
        return ItemTierType.STONE;
    }

    @Override
    public int getMaxDurability() {
        return 131;
    }
}
