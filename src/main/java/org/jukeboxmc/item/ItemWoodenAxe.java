package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWoodenAxe extends Item implements Durability {

    public ItemWoodenAxe() {
        super ( "minecraft:wooden_axe" );
    }

    @Override
    public ItemToolType getItemToolType() {
        return ItemToolType.AXE;
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
