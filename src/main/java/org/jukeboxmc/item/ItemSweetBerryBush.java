package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSweetBerryBush;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSweetBerryBush extends Item {

    public ItemSweetBerryBush() {
        super ( "minecraft:sweet_berry_bush" );
    }

    @Override
    public BlockSweetBerryBush getBlock() {
        return new BlockSweetBerryBush();
    }
}
