package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDiamondHoe extends Item implements Durability {

    public ItemDiamondHoe() {
        super ( "minecraft:diamond_hoe" );
    }

    @Override
    public ItemToolType getItemToolType() {
        return ItemToolType.HOE;
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
