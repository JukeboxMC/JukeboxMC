package org.jukeboxmc.item.enchantment;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EnchantmentSmite extends Enchantment {

    @Override
    public short getId() {
        return 10;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getWeight() {
        return 5;
    }
}
