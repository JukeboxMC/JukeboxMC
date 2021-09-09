package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCocoa;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCocoa extends Item {

    public ItemCocoa() {
        super ( "minecraft:cocoa" );
    }

    @Override
    public BlockCocoa getBlock() {
        return new BlockCocoa();
    }
}
