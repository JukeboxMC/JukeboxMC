package org.jukeboxmc.item.behavior;

import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.entity.attribute.AttributeType;
import org.jukeboxmc.item.*;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGoldenShovel extends Item implements Durability {

    public ItemGoldenShovel( Identifier identifier ) {
        super( identifier );
    }

    public ItemGoldenShovel( ItemType itemType ) {
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
    public int getMaxDurability() {
        return 32;
    }

    @Override
    public ToolType getToolType() {
        return ToolType.SHOVEL;
    }

    @Override
    public TierType getTierType() {
        return TierType.GOLD;
    }
}
