package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockJungleWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemJungleWallSign extends Item {

    public ItemJungleWallSign() {
        super( -189 );
    }

    @Override
    public BlockJungleWallSign getBlock() {
        return new BlockJungleWallSign();
    }
}
