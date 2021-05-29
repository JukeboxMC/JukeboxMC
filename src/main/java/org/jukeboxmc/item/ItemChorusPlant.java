package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockChorusPlant;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemChorusPlant extends Item {

    public ItemChorusPlant() {
        super( 240 );
    }

    @Override
    public BlockChorusPlant getBlock() {
        return new BlockChorusPlant();
    }
}
