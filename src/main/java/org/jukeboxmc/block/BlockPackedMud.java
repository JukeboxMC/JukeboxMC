package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemPackedMud;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockPackedMud extends Block {

    public BlockPackedMud() {
        super( "minecraft:packed_mud" );
    }

    @Override
    public ItemPackedMud toItem() {
        return new ItemPackedMud();
    }

    @Override
    public BlockType getType() {
        return BlockType.PACKED_MUD;
    }
}