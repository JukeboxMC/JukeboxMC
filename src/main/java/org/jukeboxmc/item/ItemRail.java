package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRail;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRail extends Item {

    public ItemRail() {
        super ( "minecraft:rail" );
    }

    @Override
    public BlockRail getBlock() {
        return new BlockRail();
    }
}
