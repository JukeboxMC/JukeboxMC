package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWeb;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWeb extends Item {

    public ItemWeb() {
        super( "minecraft:web", 30 );
    }

    @Override
    public BlockWeb getBlock() {
        return new BlockWeb();
    }
}
