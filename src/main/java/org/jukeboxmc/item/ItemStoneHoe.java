package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStoneHoe extends Item implements Durability {

    public ItemStoneHoe() {
        super ( "minecraft:stone_hoe" );
    }

    @Override
    public ItemToolType getItemToolType() {
        return ItemToolType.HOE;
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
