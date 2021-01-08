package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSpruceStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSpruceSign extends Item {

    public ItemSpruceSign() {
        super( "minecraft:spruce_sign", 566 );
    }

    @Override
    public BlockSpruceStandingSign getBlock() {
        return new BlockSpruceStandingSign();
    }
}
