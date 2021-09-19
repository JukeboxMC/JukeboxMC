package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWoodenSword extends Item implements Durability {

    public ItemWoodenSword() {
        super ( "minecraft:wooden_sword" );
    }

    @Override
    public ItemToolType getItemToolType() {
        return ItemToolType.SWORD;
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
