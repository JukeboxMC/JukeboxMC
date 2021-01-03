package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBlackstone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBlackstone extends Item {

    public ItemBlackstone() {
        super( "minecraft:blackstone", -273 );
    }

    @Override
    public Block getBlock() {
        return new BlockBlackstone();
    }
}
