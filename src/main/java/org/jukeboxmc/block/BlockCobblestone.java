package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCobblestone;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCobblestone extends Block {

    public BlockCobblestone() {
        super( "minecraft:cobblestone" );
    }

    @Override
    public ItemCobblestone toItem() {
        return new ItemCobblestone();
    }

    @Override
    public BlockType getType() {
        return BlockType.COBBLESTONE;
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
