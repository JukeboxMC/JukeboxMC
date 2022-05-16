package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
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
    public Item toItem() {
        return new ItemPackedMud();
    }

    @Override
    public BlockType getType() {
        return BlockType.PACKED_MUD;
    }

    @Override
    public double getHardness() {
        return 1;
    }
}