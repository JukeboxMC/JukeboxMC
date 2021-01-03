package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDiamondBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDiamondBlock extends Item {

    public ItemDiamondBlock() {
        super( "minecraft:diamond_block", 57 );
    }

    @Override
    public Block getBlock() {
        return new BlockDiamondBlock();
    }
}
