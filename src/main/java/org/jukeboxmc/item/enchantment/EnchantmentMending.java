package org.jukeboxmc.item.enchantment;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EnchantmentMending extends Enchantment {

    @Override
    public short getId() {
        return 26;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getWeight() {
        return 2;
    }
}
