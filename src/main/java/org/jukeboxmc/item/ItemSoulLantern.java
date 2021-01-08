package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSoulLantern;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSoulLantern extends Item {

    public ItemSoulLantern() {
        super( "minecraft:soul_lantern", -269 );
    }

    @Override
    public BlockSoulLantern getBlock() {
        return new BlockSoulLantern();
    }
}
