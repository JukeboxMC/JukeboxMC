package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemNetherreactor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockNetherreactor extends Block {

    public BlockNetherreactor() {
        super( "minecraft:netherreactor" );
    }

    @Override
    public ItemNetherreactor toItem() {
        return new ItemNetherreactor();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.NETHERREACTOR;
    }

}
