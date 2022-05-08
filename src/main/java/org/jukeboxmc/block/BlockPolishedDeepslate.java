package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemPolishedDeepslate;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPolishedDeepslate extends Block{

    public BlockPolishedDeepslate() {
        super( "minecraft:polished_deepslate" );
    }

    @Override
    public ItemPolishedDeepslate toItem() {
        return new ItemPolishedDeepslate();
    }

    @Override
    public BlockType getType() {
        return BlockType.POLISHED_DEEPSLATE;
    }

    @Override
    public double getHardness() {
        return 3.5;
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
