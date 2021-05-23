package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLantern;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLantern extends Item {

    public ItemLantern() {
        super( -208 );
    }

    @Override
    public BlockLantern getBlock() {
        return new BlockLantern();
    }
}
