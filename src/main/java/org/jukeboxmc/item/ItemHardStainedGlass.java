package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockHardStainedGlass;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemHardStainedGlass extends Item {

    public ItemHardStainedGlass() {
        super( "minecraft:hard_stained_glass", 254 );
    }

    @Override
    public BlockHardStainedGlass getBlock() {
        return new BlockHardStainedGlass();
    }
}
