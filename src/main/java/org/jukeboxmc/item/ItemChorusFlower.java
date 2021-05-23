package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockChorusFlower;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemChorusFlower extends Item {

    public ItemChorusFlower() {
        super( 200 );
    }

    @Override
    public BlockChorusFlower getBlock() {
        return new BlockChorusFlower();
    }
}
