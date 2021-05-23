package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockJungleStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemJungleSign extends Item {

    public ItemJungleSign() {
        super( 568 );
    }

    @Override
    public BlockJungleStandingSign getBlock() {
        return new BlockJungleStandingSign();
    }
}
