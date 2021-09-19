package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemNetherbrick;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockNetherBrickBlock extends Block {

    public BlockNetherBrickBlock() {
        super( "minecraft:nether_brick" );
    }

    @Override
    public ItemNetherbrick toItem() {
        return new ItemNetherbrick();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.NETHER_BRICK_BLOCK;
    }

    @Override
    public double getHardness() {
        return 2;
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
