package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemMossCarpet;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMossCarpet extends Block {

    public BlockMossCarpet() {
        super( "minecraft:moss_carpet" );
    }

    @Override
    public ItemMossCarpet toItem() {
        return new ItemMossCarpet();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.MOSS_CARPET;
    }

    @Override
    public double getHardness() {
        return 0.1;
    }

}
