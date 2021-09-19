package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGoldenHoe extends Item implements Durability {

    public ItemGoldenHoe() {
        super ( "minecraft:golden_hoe" );
    }

    @Override
    public ItemToolType getItemToolType() {
        return ItemToolType.HOE;
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
