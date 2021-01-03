package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCrimsonStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonStandingSign extends Item {

    public ItemCrimsonStandingSign() {
        super( "minecraft:crimson_standing_sign", -250 );
    }

    @Override
    public Block getBlock() {
        return new BlockCrimsonStandingSign();
    }
}
