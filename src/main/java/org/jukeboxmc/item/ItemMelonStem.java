package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockMelonStem;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemMelonStem extends Item {

    public ItemMelonStem() {
        super( 105 );
    }

    @Override
    public BlockMelonStem getBlock() {
        return new BlockMelonStem();
    }
}
