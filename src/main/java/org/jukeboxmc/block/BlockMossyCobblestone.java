package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemMossyCobblestone;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMossyCobblestone extends Block {

    public BlockMossyCobblestone() {
        super( "minecraft:mossy_cobblestone" );
    }

    @Override
    public ItemMossyCobblestone toItem() {
        return new ItemMossyCobblestone();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.MOSSY_COBBLESTONE;
    }

    @Override
    public double getHardness() {
        return 2;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }
}
