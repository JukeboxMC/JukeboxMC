package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGoldenAxe extends Item implements Durability {

    public ItemGoldenAxe() {
        super ( "minecraft:golden_axe" );
    }

    @Override
    public ItemToolType getItemToolType() {
        return ItemToolType.AXE;
    }

    @Override
    public ItemTierType getTierType() {
        return ItemTierType.GOLD;
    }

    @Override
    public int getMaxDurability() {
        return 32;
    }
}
