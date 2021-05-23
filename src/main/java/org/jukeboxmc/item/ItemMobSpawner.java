package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockMobSpawner;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemMobSpawner extends Item {

    public ItemMobSpawner() {
        super( 52 );
    }

    @Override
    public BlockMobSpawner getBlock() {
        return new BlockMobSpawner();
    }
}
