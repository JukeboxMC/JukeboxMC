package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.entity.attribute.AttributeType;
import org.jukeboxmc.item.*;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStonePickaxe extends Item implements Durability {

    public ItemStonePickaxe( Identifier identifier ) {
        super( identifier );
    }

    public ItemStonePickaxe( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public void addToHand(@NotNull Player player ) {
        Attribute attribute = player.getAttribute( AttributeType.ATTACK_DAMAGE );
        attribute.setCurrentValue( 3 );
    }

    @Override
    public void removeFromHand(@NotNull Player player ) {
        Attribute attribute = player.getAttribute( AttributeType.ATTACK_DAMAGE );
        attribute.setCurrentValue( attribute.getMinValue() );
    }

    @Override
    public int getMaxDurability() {
        return 131;
    }

    @Override
    public @NotNull ToolType getToolType() {
        return ToolType.PICKAXE;
    }

    @Override
    public @NotNull TierType getTierType() {
        return TierType.STONE;
    }
}
