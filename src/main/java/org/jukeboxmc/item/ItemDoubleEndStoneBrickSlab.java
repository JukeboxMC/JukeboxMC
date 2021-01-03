package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDoubleEndStoneBrickSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDoubleEndStoneBrickSlab extends Item {

    public ItemDoubleEndStoneBrickSlab() {
        super( "minecraft:double_stone_slab3", -162 );
    }

    @Override
    public Block getBlock() {
        return new BlockDoubleEndStoneBrickSlab();
    }
}
