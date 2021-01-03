package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCoral;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoral extends Item {

    public ItemCoral() {
        super( "minecraft:coral", -131 );
    }

    @Override
    public Block getBlock() {
        return new BlockCoral();
    }
}
