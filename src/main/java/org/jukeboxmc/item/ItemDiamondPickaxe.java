package org.jukeboxmc.item;

import org.jukeboxmc.item.type.Durability;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDiamondPickaxe extends Item implements Durability {

    public ItemDiamondPickaxe() {
        super ( "minecraft:diamond_pickaxe" );
    }

    @Override
    public ItemToolType getItemToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public ItemTierType getTierType() {
        return ItemTierType.DIAMOND;
    }

    @Override
    public int getMaxDurability() {
        return 1561;
    }
}
