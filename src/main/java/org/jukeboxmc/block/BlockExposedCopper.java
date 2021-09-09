package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemExposedCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockExposedCopper extends Block{

    public BlockExposedCopper() {
        super( "minecraft:exposed_copper" );
    }

    @Override
    public ItemExposedCopper toItem() {
        return new ItemExposedCopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.EXPOSED_COPPER;
    }
}
