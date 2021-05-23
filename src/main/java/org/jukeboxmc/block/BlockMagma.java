package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemMagma;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMagma extends Block {

    public BlockMagma() {
        super( "minecraft:magma" );
    }

    @Override
    public ItemMagma toItem() {
        return new ItemMagma();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.MAGMA;
    }

}
