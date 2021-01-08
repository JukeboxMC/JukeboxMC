package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPodzol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPodzol extends Item {

    public ItemPodzol() {
        super( "minecraft:podzol", 243 );
    }

    @Override
    public BlockPodzol getBlock() {
        return new BlockPodzol();
    }
}
