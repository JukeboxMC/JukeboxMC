package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCrackedDeepslateTiles;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCrackedDeepslateTiles extends Block {

    public BlockCrackedDeepslateTiles() {
        super( "minecraft:cracked_deepslate_tiles" );
    }

    @Override
    public ItemCrackedDeepslateTiles toItem() {
        return new ItemCrackedDeepslateTiles();
    }

    @Override
    public BlockType getType() {
        return BlockType.CRACKED_DEEPSLATE_TILES;
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
