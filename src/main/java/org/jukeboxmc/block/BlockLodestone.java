package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemLodestone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLodestone extends Block {

    public BlockLodestone() {
        super( "minecraft:lodestone" );
    }

    @Override
    public ItemLodestone toItem() {
        return new ItemLodestone();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LODESTONE;
    }

}
