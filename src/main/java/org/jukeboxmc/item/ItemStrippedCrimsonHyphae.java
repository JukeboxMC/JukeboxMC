package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStrippedCrimsonHyphae;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStrippedCrimsonHyphae extends Item {

    public ItemStrippedCrimsonHyphae() {
        super( -300 );
    }

    @Override
    public BlockStrippedCrimsonHyphae getBlock() {
        return new BlockStrippedCrimsonHyphae();
    }
}
