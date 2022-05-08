package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCrackedDeepslateBricks;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCrackedDeepslateBricks extends Block {

    public BlockCrackedDeepslateBricks() {
        super( "minecraft:cracked_deepslate_bricks" );
    }

    @Override
    public ItemCrackedDeepslateBricks toItem() {
        return new ItemCrackedDeepslateBricks();
    }

    @Override
    public BlockType getType() {
        return BlockType.CRACKED_DEEPSLATE_BRICKS;
    }

    @Override
    public double getHardness() {
        return 3.5;
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
