package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCoalBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoalOre extends Item {

    public ItemCoalOre() {
        super( "minecraft:coal_ore", 16 );
    }

    @Override
    public Block getBlock() {
        return new BlockCoalBlock();n
    }
}
