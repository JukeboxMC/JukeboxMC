package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNetheritePickaxe extends Item implements Durability {

    public ItemNetheritePickaxe() {
        super ( "minecraft:netherite_pickaxe" );
    }

    @Override
    public ItemToolType getItemToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public ItemTierType getTierType() {
        return ItemTierType.NETHERITE;
    }

    @Override
    public int getMaxDurability() {
        return 2031;
    }
}
