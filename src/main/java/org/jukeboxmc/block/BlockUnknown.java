package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemUnknown;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockUnknown extends Block {

    public BlockUnknown() {
        super( "minecraft:unknown" );
    }

    @Override
    public ItemUnknown toItem() {
        return new ItemUnknown();
    }

    @Override
    public BlockType getType() {
        return BlockType.UNKNOWN;
    }
}