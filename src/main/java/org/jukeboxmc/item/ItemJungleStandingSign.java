package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockJungleStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemJungleStandingSign extends Item {

    public ItemJungleStandingSign() {
        super ( "minecraft:jungle_standing_sign" );
    }

    @Override
    public BlockJungleStandingSign getBlock() {
        return new BlockJungleStandingSign();
    }
}
