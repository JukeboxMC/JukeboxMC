package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemHardenedClay;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockHardenedClay extends Block {

    public BlockHardenedClay() {
        super( "minecraft:hardened_clay" );
    }

    @Override
    public ItemHardenedClay toItem() {
        return new ItemHardenedClay();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.HARDENED_CLAY;
    }

    @Override
    public double getHardness() {
        return 1.25;
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
