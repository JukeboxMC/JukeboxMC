package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDropper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDropper extends Item {

    public ItemDropper() {
        super ( "minecraft:dropper" );
    }

    @Override
    public BlockDropper getBlock() {
        return new BlockDropper();
    }
}
