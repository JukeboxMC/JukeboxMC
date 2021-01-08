package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockMobSpawner;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemMobSpawner extends Item {

    public ItemMobSpawner() {
        super( "minecraft:mob_spawner", 52 );
    }

    @Override
    public BlockMobSpawner getBlock() {
        return new BlockMobSpawner();
    }
}
