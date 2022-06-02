package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemMangrovePropaguleHanging;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMangrovePropaguleHanging extends Block {

    public BlockMangrovePropaguleHanging() {
        super( "minecraft:mangrove_propagule_hanging" );
    }

    @Override
    public ItemMangrovePropaguleHanging toItem() {
        return new ItemMangrovePropaguleHanging();
    }

    @Override
    public BlockType getType() {
        return BlockType.MANGROVE_PROPAGULE_HANGING;
    }
}