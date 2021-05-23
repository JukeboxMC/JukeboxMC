package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCrimsonStem;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonStem extends Item {

    public ItemCrimsonStem() {
        super( -225 );
    }

    @Override
    public BlockCrimsonStem getBlock() {
        return new BlockCrimsonStem();
    }
}
