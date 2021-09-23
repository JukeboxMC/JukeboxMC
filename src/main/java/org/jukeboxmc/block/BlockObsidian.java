package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemObsidian;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockObsidian extends Block {

    public BlockObsidian() {
        super( "minecraft:obsidian" );
    }

    @Override
    public ItemObsidian toItem() {
        return new ItemObsidian();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.OBSIDIAN;
    }

    @Override
    public double getHardness() {
        return 35;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public ItemTierType getTierType() {
        return ItemTierType.DIAMOND;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

}
