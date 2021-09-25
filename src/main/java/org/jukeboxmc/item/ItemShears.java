package org.jukeboxmc.item;

import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemShears extends Item {

    public ItemShears() {
        super ( "minecraft:shears" );
    }

    @Override
    public ItemToolType getItemToolType() {
        return ItemToolType.SHEARS;
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }
}
