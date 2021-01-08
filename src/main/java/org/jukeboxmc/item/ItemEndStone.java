package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockEndStone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemEndStone extends Item {

    public ItemEndStone() {
        super( "minecraft:end_stone", 121 );
    }

    @Override
    public BlockEndStone getBlock() {
        return new BlockEndStone();
    }
}
