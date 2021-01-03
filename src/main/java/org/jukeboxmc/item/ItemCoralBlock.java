package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCoralBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoralBlock extends Item {

    public ItemCoralBlock() {
        super( "minecraft:coral_block", -132 );
    }

    @Override
    public Block getBlock() {
        return new BlockCoralBlock();
    }
}
