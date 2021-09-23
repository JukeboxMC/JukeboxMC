package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDeepslateTiles;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslateTiles extends Block{

    public BlockDeepslateTiles() {
        super( "minecraft:deepslate_tiles" );
    }

    @Override
    public ItemDeepslateTiles toItem() {
        return new ItemDeepslateTiles();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DEEPSLATE_TILES;
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
