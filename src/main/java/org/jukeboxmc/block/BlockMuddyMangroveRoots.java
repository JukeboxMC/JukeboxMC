package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemMuddyMangroveRoots;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMuddyMangroveRoots extends Block {

    public BlockMuddyMangroveRoots() {
        super( "minecraft:muddy_mangrove_roots" );
    }

    @Override
    public Item toItem() {
        return new ItemMuddyMangroveRoots();
    }

    @Override
    public BlockType getType() {
        return BlockType.MUDDY_MANGROVE_ROOTS;
    }

    @Override
    public double getHardness() {
        return 0.7;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.SHOVEL;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}