package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemGlass;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGlass extends Block {

    public BlockGlass() {
        super( "minecraft:glass" );
    }

    @Override
    public ItemGlass toItem() {
        return new ItemGlass();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GLASS;
    }

}
