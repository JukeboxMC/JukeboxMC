package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLoom;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLoom extends Item {

    public ItemLoom() {
        super( -204 );
    }

    @Override
    public BlockLoom getBlock() {
        return new BlockLoom();
    }
}
