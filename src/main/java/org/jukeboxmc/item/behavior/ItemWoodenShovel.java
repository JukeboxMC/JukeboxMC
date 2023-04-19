package org.jukeboxmc.item.behavior;

import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.entity.attribute.AttributeType;
import org.jukeboxmc.item.*;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWoodenShovel extends Item implements Durability, Burnable {

    public ItemWoodenShovel( Identifier identifier ) {
        super( identifier );
    }

    public ItemWoodenShovel( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public void addToHand( Player player ) {
        Attribute attribute = player.getAttribute( AttributeType.ATTACK_DAMAGE );
        attribute.setCurrentValue( 1 );
    }

    @Override
    public void removeFromHand( Player player ) {
        Attribute attribute = player.getAttribute( AttributeType.ATTACK_DAMAGE );
        attribute.setCurrentValue( attribute.getMinValue() );
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 200 );
    }

    @Override
    public int getMaxDurability() {
        return 59;
    }

    @Override
    public ToolType getToolType() {
        return ToolType.SHOVEL;
    }

    @Override
    public TierType getTierType() {
        return TierType.WOODEN;
    }
}
