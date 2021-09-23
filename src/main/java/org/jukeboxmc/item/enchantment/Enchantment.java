package org.jukeboxmc.item.enchantment;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class Enchantment {

    private short level;

    public abstract short getId();

    public abstract int getMaxLevel();

    public abstract int getWeight();

    public short getLevel() {
        return this.level;
    }

    public Enchantment setLevel( short level ) {
        this.level = level;
        return this;
    }

    @Override
    public String toString() {
        return "Enchantment{" +
                "id=" + this.getId() + ", " +
                "level=" + this.getLevel() + ", " +
                "maxlevel=" + this.getMaxLevel() + ", " +
                "weight=" + this.getWeight() +
                '}';
    }
}
