package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemMangroveFenceGate;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMangroveFenceGate extends Block {

    public BlockMangroveFenceGate() {
        super( "minecraft:mangrove_fence_gate" );
    }

    @Override
    public Item toItem() {
        return new ItemMangroveFenceGate();
    }

    @Override
    public BlockType getType() {
        return BlockType.MANGROVE_FENCE_GATE;
    }
}