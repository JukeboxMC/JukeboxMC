package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCarvedPumpkin;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCarvedPumpkin extends Item {

    public ItemCarvedPumpkin() {
        super(  -155 );
    }

    @Override
    public BlockCarvedPumpkin getBlock() {
        return new BlockCarvedPumpkin();
    }
}
