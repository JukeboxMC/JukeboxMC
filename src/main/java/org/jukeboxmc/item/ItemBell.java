package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBell;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBell extends Item {

    public ItemBell() {
        super ( "minecraft:bell" );
    }

    @Override
    public BlockBell getBlock() {
        return new BlockBell();
    }
}
