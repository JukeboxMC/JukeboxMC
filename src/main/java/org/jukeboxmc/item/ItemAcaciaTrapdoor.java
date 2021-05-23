package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockAcaciaTrapdoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaTrapdoor extends Item {

    public ItemAcaciaTrapdoor() {
        super( -145 );
    }

    @Override
    public BlockAcaciaTrapdoor getBlock() {
        return new BlockAcaciaTrapdoor();
    }
}
