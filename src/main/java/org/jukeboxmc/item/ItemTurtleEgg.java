package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockTurtleEgg;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemTurtleEgg extends Item {

    public ItemTurtleEgg() {
        super ( "minecraft:turtle_egg" );
    }

    @Override
    public BlockTurtleEgg getBlock() {
        return new BlockTurtleEgg();
    }
}
