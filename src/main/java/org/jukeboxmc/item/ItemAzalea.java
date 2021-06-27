package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockAzalea;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAzalea extends Item{

    public ItemAzalea() {
        super( "minecraft:azalea" );
    }

    @Override
    public BlockAzalea getBlock() {
        return new BlockAzalea();
    }
}
