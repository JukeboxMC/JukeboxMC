package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemMossBlock;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMossBlock extends Block{

    public BlockMossBlock() {
        super( "minecraft:moss_block" );
    }

    @Override
    public ItemMossBlock toItem() {
        return new ItemMossBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.MOSS_BLOCK;
    }

    @Override
    public double getHardness() {
        return 0.1;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.HOE;
    }
}
