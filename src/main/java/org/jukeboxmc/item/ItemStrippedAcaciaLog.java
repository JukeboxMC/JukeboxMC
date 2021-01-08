package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStrippedAcaciaLog;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStrippedAcaciaLog extends Item {

    public ItemStrippedAcaciaLog() {
        super( "minecraft:stripped_acacia_log", -8 );
    }

    @Override
    public BlockStrippedAcaciaLog getBlock() {
        return new BlockStrippedAcaciaLog();
    }
}
