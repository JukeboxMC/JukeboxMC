package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWitherRose;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWitherRose extends Block {

    public BlockWitherRose() {
        super( "minecraft:wither_rose" );
    }

    @Override
    public ItemWitherRose toItem() {
        return new ItemWitherRose();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WITHER_ROSE;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
