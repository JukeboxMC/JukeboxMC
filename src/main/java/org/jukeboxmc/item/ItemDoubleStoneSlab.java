package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDoubleStoneSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDoubleStoneSlab extends Item {

    public ItemDoubleStoneSlab() {
        super( "minecraft:double_stone_slab", 44 );
    }

    @Override
    public Block getBlock() {
        return new BlockDoubleStoneSlab();
    }
}
