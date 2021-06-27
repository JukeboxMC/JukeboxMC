package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockMossCarpet;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemMossCarpet extends Item{

    public ItemMossCarpet() {
        super( "minecraft:moss_carpet" );
    }

    @Override
    public BlockMossCarpet getBlock() {
        return new BlockMossCarpet();
    }
}
