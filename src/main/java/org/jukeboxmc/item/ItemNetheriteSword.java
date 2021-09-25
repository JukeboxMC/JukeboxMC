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
public class ItemNetheriteSword extends Item implements Durability {

    public ItemNetheriteSword() {
        super ( "minecraft:netherite_sword" );
    }

    @Override
    public void addToHand( Player player ) {
        Attribute attribute = player.getAttribute( AttributeType.ATTACK_DAMAGE );
        attribute.setCurrentValue( 8 );
    }

    @Override
    public void removeFromHand( Player player ) {
        Attribute attribute = player.getAttribute( AttributeType.ATTACK_DAMAGE );
        attribute.setCurrentValue( attribute.getMinValue() );
    }

    @Override
    public ItemToolType getItemToolType() {
        return ItemToolType.SWORD;
    }

    @Override
    public ItemTierType getTierType() {
        return ItemTierType.NETHERITE;
    }

    @Override
    public int getMaxDurability() {
        return 2031;
    }
}
