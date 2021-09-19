package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWoodenHoe extends Item implements Durability {

    public ItemWoodenHoe() {
        super ( "minecraft:wooden_hoe" );
    }

    @Override
    public ItemToolType getItemToolType() {
        return ItemToolType.HOE;
    }

    @Override
    public ItemTierType getTierType() {
        return ItemTierType.WOODEN;
    }

    @Override
    public int getMaxDurability() {
        return 131;
    }
}
