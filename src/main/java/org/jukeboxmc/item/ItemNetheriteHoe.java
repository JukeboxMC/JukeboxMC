package org.jukeboxmc.item;

import org.jukeboxmc.item.type.Durability;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNetheriteHoe extends Item implements Durability {

    public ItemNetheriteHoe() {
        super ( "minecraft:netherite_hoe" );
    }

    @Override
    public ItemToolType getItemToolType() {
        return ItemToolType.HOE;
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
