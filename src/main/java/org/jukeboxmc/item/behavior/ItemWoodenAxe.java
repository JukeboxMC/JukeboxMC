package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
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
public class ItemWoodenAxe extends Item implements Durability, Burnable {

    public ItemWoodenAxe( Identifier identifier ) {
        super( identifier );
    }

    public ItemWoodenAxe( ItemType itemType ) {
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
    public Duration getBurnTime() {
        return Duration.ofMillis( 200 );
    }

    @Override
    public int getMaxDurability() {
        return 59;
    }

    @Override
    public @NotNull ToolType getToolType() {
        return ToolType.AXE;
    }

    @Override
    public @NotNull TierType getTierType() {
        return TierType.WOODEN;
    }
}
