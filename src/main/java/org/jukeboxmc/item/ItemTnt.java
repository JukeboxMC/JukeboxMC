package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockTnt;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemTnt extends Item {

    public ItemTnt() {
        super( "minecraft:tnt", 46 );
    }

    @Override
    public BlockTnt getBlock() {
        return new BlockTnt();
    }
}
