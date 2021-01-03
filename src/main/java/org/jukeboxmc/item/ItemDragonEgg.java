package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDragonEgg;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDragonEgg extends Item {

    public ItemDragonEgg() {
        super( "minecraft:dragon_egg", 122 );
    }

    @Override
    public Block getBlock() {
        return new BlockDragonEgg();
    }
}
