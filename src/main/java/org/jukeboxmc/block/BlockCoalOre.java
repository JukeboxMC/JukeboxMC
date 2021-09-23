package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCoalOre;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCoalOre extends Block {

    public BlockCoalOre() {
        super( "minecraft:coal_ore" );
    }

    @Override
    public ItemCoalOre toItem() {
        return new ItemCoalOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.COAL_ORE;
    }

    @Override
    public double getHardness() {
        return 3;
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
