package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockJackOLantern;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemJackOLantern extends Item {

    public ItemJackOLantern() {
        super( 91 );
    }

    @Override
    public BlockJackOLantern getBlock() {
        return new BlockJackOLantern();
    }
}
