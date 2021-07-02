package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLava;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLavaBucket extends AbstractItemBucket {

    public ItemLavaBucket() {
        super ( "minecraft:lava_bucket" );
    }

    @Override
    public BlockLava getBlock() {
        return new BlockLava();
    }
}
