package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCobbledDeepslate;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCobbledDeepslate extends Block {

    public BlockCobbledDeepslate() {
        super( "minecraft:cobbled_deepslate" );
    }

    @Override
    public ItemCobbledDeepslate toItem() {
        return new ItemCobbledDeepslate();
    }

    @Override
    public BlockType getType() {
        return BlockType.COBBLED_DEEPSLATE;
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
