package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemRedMushroom;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRedMushroom extends Block {

    public BlockRedMushroom() {
        super( "minecraft:red_mushroom" );
    }

    @Override
    public ItemRedMushroom toItem() {
        return new ItemRedMushroom();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.RED_MUSHROOM;
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
