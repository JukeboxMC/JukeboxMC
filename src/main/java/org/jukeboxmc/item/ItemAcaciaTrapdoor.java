package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockAcaciaTrapdoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaTrapdoor extends Item {

    public ItemAcaciaTrapdoor() {
        super( "minecraft:acacia_trapdoor", -145 );
    }

    @Override
    public Block getBlock() {
        return new BlockAcaciaTrapdoor();
    }
}
