package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSpruceStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSpruceStandingSign extends Item {

    public ItemSpruceStandingSign() {
        super ( "minecraft:spruce_standing_sign" );
    }

    @Override
    public BlockSpruceStandingSign getBlock() {
        return new BlockSpruceStandingSign();
    }

    @Override
    public int getMaxAmount() {
        return 16;
    }
}
