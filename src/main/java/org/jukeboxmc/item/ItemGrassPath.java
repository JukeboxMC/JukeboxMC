package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockGrassPath;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGrassPath extends Item {

    public ItemGrassPath() {
        super( "minecraft:grass_path", 198 );
    }

    @Override
    public BlockGrassPath getBlock() {
        return new BlockGrassPath();
    }
}
