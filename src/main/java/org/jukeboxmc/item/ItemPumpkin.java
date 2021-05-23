package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPumpkin;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPumpkin extends Item {

    public ItemPumpkin() {
        super( 86 );
    }

    @Override
    public BlockPumpkin getBlock() {
        return new BlockPumpkin();
    }
}
