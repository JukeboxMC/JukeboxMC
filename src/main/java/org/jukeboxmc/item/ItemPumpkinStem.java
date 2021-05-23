package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPumpkinStem;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPumpkinStem extends Item {

    public ItemPumpkinStem() {
        super( 104 );
    }

    @Override
    public BlockPumpkinStem getBlock() {
        return new BlockPumpkinStem();
    }
}
