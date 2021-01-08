package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockUnpoweredRepeater;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRepeater extends Item {

    public ItemRepeater() {
        super( "minecraft:repeater", 417 );
    }

    @Override
    public BlockUnpoweredRepeater getBlock() {
        return new BlockUnpoweredRepeater();
    }
}
