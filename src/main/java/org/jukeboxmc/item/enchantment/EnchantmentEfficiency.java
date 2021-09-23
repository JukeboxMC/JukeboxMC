package org.jukeboxmc.item.enchantment;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EnchantmentEfficiency extends Enchantment {

    @Override
    public short getId() {
        return 15;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getWeight() {
        return 10;
    }
}
