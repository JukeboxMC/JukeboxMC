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
public class ItemDiamondPickaxe extends Item implements Durability {

    public ItemDiamondPickaxe( Identifier identifier ) {
        super( identifier );
    }

    public ItemDiamondPickaxe( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public void addToHand(@NotNull Player player ) {
        Attribute attribute = player.getAttribute( AttributeType.ATTACK_DAMAGE );
        attribute.setCurrentValue( 5 );
    }

    @Override
    public void removeFromHand(@NotNull Player player ) {
        Attribute attribute = player.getAttribute( AttributeType.ATTACK_DAMAGE );
        attribute.setCurrentValue( attribute.getMinValue() );
    }

    @Override
    public int getMaxDurability() {
        return 1561;
    }

    @Override
    public @NotNull ToolType getToolType() {
        return ToolType.PICKAXE;
    }

    @Override
    public @NotNull TierType getTierType() {
        return TierType.DIAMOND;
    }
}
