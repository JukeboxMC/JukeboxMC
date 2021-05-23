package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemConduit;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockConduit extends Block {

    public BlockConduit() {
        super( "minecraft:conduit" );
    }

    @Override
    public ItemConduit toItem() {
        return new ItemConduit();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CONDUIT;
    }

}
