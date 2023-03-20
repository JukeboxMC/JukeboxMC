package org.jukeboxmc.item.enchantment;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class Enchantment {

    private short level;

    public abstract short getId();

    public abstract int getMaxLevel();

    public short getLevel() {
        return this.level;
    }

    public @NotNull Enchantment setLevel(short level ) {
        this.level = level;
        return this;
    }

    public static <T extends Enchantment> @NotNull T create(EnchantmentType enchantmentType ) {
        try {
            return (T) EnchantmentRegistry.getEnchantmentClass( enchantmentType ).getConstructor().newInstance();
        } catch ( InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e ) {
            throw new RuntimeException( e );
        }
    }
}
