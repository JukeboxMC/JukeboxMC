package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBirchTrapdoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBirchTrapdoor extends BlockTrapdoor {

    public BlockBirchTrapdoor() {
        super( "minecraft:birch_trapdoor" );
    }

    @Override
    public ItemBirchTrapdoor toItem() {
        return new ItemBirchTrapdoor();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BIRCH_TRAPDOOR;
    }

}
