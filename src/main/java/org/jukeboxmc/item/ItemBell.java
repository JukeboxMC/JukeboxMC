package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBell;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBell extends Item {

    public ItemBell() {
        super( "minecraft:bell", -206 );
    }

    @Override
    public Block getBlock() {
        return new BlockBell();
    }
}
