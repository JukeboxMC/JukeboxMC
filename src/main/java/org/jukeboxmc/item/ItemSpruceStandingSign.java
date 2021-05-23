package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSpruceStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSpruceStandingSign extends Item {

    public ItemSpruceStandingSign() {
        super( -181 );
    }

    @Override
    public BlockSpruceStandingSign getBlock() {
        return new BlockSpruceStandingSign();
    }
}
