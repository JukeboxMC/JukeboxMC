package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemSmoothBasalt;

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
}
