package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCarvedPumpkin;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCarvedPumpkin extends Item {

    public ItemCarvedPumpkin() {
        super( "minecraft:carved_pumpkin", -155 );
    }

    @Override
    public Block getBlock() {
        return new BlockCarvedPumpkin();
    }
}
