package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockAcaciaTrapdoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaTrapdoor extends Item {

    public ItemAcaciaTrapdoor() {
        super( "minecraft:acacia_trapdoor" );
    }

    @Override
    public BlockAcaciaTrapdoor getBlock() {
        return new BlockAcaciaTrapdoor();
    }
}
