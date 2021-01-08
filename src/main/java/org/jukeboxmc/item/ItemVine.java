package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockVine;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemVine extends Item {

    public ItemVine() {
        super( "minecraft:vine", 106 );
    }

    @Override
    public BlockVine getBlock() {
        return new BlockVine();
    }
}
