package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemAzaleaLeaves;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAzaleaLeaves extends Block{

    public BlockAzaleaLeaves() {
        super( "minecraft:azalea_leaves" );
    }

    @Override
    public ItemAzaleaLeaves toItem() {
        return new ItemAzaleaLeaves();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.AZALEA_LEAVES;
    }
}
