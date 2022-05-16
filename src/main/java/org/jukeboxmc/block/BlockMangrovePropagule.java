package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemMangrovePropagule;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMangrovePropagule extends Block {

    public BlockMangrovePropagule() {
        super( "minecraft:mangrove_propagule" );
    }

    @Override
    public Item toItem() {
        return new ItemMangrovePropagule();
    }

    @Override
    public BlockType getType() {
        return BlockType.MANGROVE_PROPAGULE;
    }

    @Override
    public double getHardness() {
        return 0;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}