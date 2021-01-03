package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBrickBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBrickBlock extends Item {

    public ItemBrickBlock() {
        super( "minecraft:brick_block", 45 );
    }

    @Override
    public Block getBlock() {
        return new BlockBrickBlock();
    }
}
