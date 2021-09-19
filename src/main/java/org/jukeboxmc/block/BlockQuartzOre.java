package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemQuartzOre;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockQuartzOre extends Block {

    public BlockQuartzOre() {
        super( "minecraft:quartz_ore" );
    }

    @Override
    public ItemQuartzOre toItem() {
        return new ItemQuartzOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.QUARTZ_ORE;
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
