package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAir extends Item {

    public ItemAir() {
        super( "minecraft:air" );
        this.amount = 0;
    }

    @Override
    public ItemType getType() {
        return ItemType.AIR;
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }
}
