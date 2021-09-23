package org.jukeboxmc.item;

import org.jukeboxmc.item.type.Durability;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNetheriteAxe extends Item implements Durability {

    public ItemNetheriteAxe() {
        super ( "minecraft:netherite_axe" );
    }

    @Override
    public ItemToolType getItemToolType() {
        return ItemToolType.AXE;
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
