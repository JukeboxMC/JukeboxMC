package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemAmethystCluster;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAmethystCluster extends Block {

    public BlockAmethystCluster() {
        super( "minecraft:amethyst_cluster" );
    }

    @Override
    public ItemAmethystCluster toItem() {
        return new ItemAmethystCluster();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.AMETHYST_CLUSTER;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
