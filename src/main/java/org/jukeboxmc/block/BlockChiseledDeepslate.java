package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemChiseledDeepslate;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockChiseledDeepslate extends Block {

    public BlockChiseledDeepslate() {
        super( "minecraft:chiseled_deepslate" );
    }

    @Override
    public ItemChiseledDeepslate toItem() {
        return new ItemChiseledDeepslate();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CHISELED_DEEPSLATE;
    }

    @Override
    public double getHardness() {
        return 3;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }
}
