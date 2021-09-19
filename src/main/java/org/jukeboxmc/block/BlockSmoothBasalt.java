package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemSmoothBasalt;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSmoothBasalt extends Block{

    public BlockSmoothBasalt() {
        super( "minecraft:smooth_basalt" );
    }

    @Override
    public ItemSmoothBasalt toItem() {
        return new ItemSmoothBasalt();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SMOOTH_BASALT;
    }

    @Override
    public double getHardness() {
        return 1.25;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }
}
