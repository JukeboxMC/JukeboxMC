package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSmoker;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSmoker extends Item {

    public ItemSmoker() {
        super ( "minecraft:smoker" );
    }

    @Override
    public BlockSmoker getBlock() {
        return new BlockSmoker();
    }
}
