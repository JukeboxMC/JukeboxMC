package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemTrapdoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWoodenTrapdoor extends BlockTrapdoor {

    public BlockWoodenTrapdoor() {
        super( "minecraft:trapdoor" );
    }

    @Override
    public ItemTrapdoor toItem() {
        return new ItemTrapdoor();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WOODEN_TRAPDOOR;
    }

}
