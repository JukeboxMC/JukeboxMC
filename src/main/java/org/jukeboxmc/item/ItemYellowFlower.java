package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockYellowFlower;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemYellowFlower extends Item {

    public ItemYellowFlower() {
        super( 37 );
    }

    @Override
    public BlockYellowFlower getBlock() {
        return new BlockYellowFlower();
    }
}
