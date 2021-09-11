package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemGrass;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGrass extends Block {

    public BlockGrass() {
        super( "minecraft:grass" );
    }

    @Override
    public ItemGrass toItem() {
        return new ItemGrass();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GRASS;
    }

    @Override
    public boolean canPassThrough() {
        return false;
    }
}
