package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemIronAxe extends Item implements Durability {

    public ItemIronAxe() {
        super ( "minecraft:iron_axe" );
    }

    @Override
    public ItemToolType getItemToolType() {
        return ItemToolType.AXE;
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
