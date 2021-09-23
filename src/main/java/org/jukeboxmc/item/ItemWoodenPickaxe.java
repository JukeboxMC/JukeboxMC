package org.jukeboxmc.item;

import org.jukeboxmc.item.type.Durability;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWoodenPickaxe extends Item implements Durability {

    public ItemWoodenPickaxe() {
        super ( "minecraft:wooden_pickaxe" );
    }

    @Override
    public ItemToolType getItemToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public ItemTierType getTierType() {
        return ItemTierType.WOODEN;
    }

    @Override
    public int getMaxDurability() {
        return 59;
    }
}
