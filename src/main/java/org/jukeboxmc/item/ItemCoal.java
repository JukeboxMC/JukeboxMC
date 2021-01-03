package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCoalBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoal extends Item {

    public ItemCoal() {
        super( "minecraft:coal", 302 );
    }

    @Override
    public Block getBlock() {
        return new BlockCoalBlock();
    }
}
