package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockAcaciaLog;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaLog extends Item {

    public ItemAcaciaLog() {
        super( "minecraft:log2", 162 );
    }

    @Override
    public Block getBlock() {
        return new BlockAcaciaLog();
    }
}
