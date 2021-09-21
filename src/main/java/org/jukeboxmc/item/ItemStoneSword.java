package org.jukeboxmc.item;

import org.jukeboxmc.item.type.Durability;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStoneSword extends Item implements Durability {

    public ItemStoneSword() {
        super ( "minecraft:stone_sword" );
    }

    @Override
    public ItemToolType getItemToolType() {
        return ItemToolType.SWORD;
    }

    @Override
    public ItemTierType getTierType() {
        return ItemTierType.STONE;
    }

    @Override
    public int getMaxDurability() {
        return 131;
    }
}
