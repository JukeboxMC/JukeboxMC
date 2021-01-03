package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDarkOakStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDarkOakStandingSign extends Item {

    public ItemDarkOakStandingSign() {
        super( "minecraft:darkoak_standing_sign", -192 );
    }

    @Override
    public Block getBlock() {
        return new BlockDarkOakStandingSign();
    }
}
