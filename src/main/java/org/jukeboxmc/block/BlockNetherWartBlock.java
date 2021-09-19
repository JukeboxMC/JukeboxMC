package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemNetherWartBlock;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockNetherWartBlock extends Block {

    public BlockNetherWartBlock() {
        super( "minecraft:nether_wart_block" );
    }

    @Override
    public ItemNetherWartBlock toItem() {
        return new ItemNetherWartBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.NETHER_WART_BLOCK;
    }

    @Override
    public double getHardness() {
        return 1;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.HOE;
    }
}
