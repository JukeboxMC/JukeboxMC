package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemIronPickaxe extends Item implements Durability {

    public ItemIronPickaxe() {
        super ( "minecraft:iron_pickaxe" );
    }

    @Override
    public ItemToolType getItemToolType() {
        return ItemToolType.PICKAXE;
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
