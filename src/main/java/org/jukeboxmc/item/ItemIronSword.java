package org.jukeboxmc.item;

import org.jukeboxmc.item.type.Durability;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemIronSword extends Item implements Durability {

    public ItemIronSword() {
        super ( "minecraft:iron_sword" );
    }

    @Override
    public ItemToolType getItemToolType() {
        return ItemToolType.SWORD;
    }

    @Override
    public ItemTierType getTierType() {
        return ItemTierType.IRON;
    }

    @Override
    public int getMaxDurability() {
        return 250;
    }
}
