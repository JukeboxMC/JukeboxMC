package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDeny;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDeny extends Item {

    public ItemDeny() {
        super( "minecraft:deny", 211 );
    }

    @Override
    public Block getBlock() {
        return new BlockDeny();
    }
}
