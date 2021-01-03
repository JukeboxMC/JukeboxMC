package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCommandBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCommandBlock extends Item {

    public ItemCommandBlock() {
        super( "minecraft:command_block", 137 );
    }

    @Override
    public Block getBlock() {
        return new BlockCommandBlock();
    }
}
