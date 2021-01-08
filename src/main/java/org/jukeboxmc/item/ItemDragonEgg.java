package org.jukeboxmc.item;

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
    public BlockDragonEgg getBlock() {
        return new BlockDragonEgg();
    }
}
