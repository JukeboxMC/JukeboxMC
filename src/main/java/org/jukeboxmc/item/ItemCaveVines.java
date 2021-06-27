package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCaveVines;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCaveVines extends Item {

    public ItemCaveVines() {
        super( "minecraft:cave_vines" );
    }

    @Override
    public BlockCaveVines getBlock() {
        return new BlockCaveVines();
    }
}
