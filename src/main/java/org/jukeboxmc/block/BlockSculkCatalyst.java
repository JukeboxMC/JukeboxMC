package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemSculkCatalyst;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSculkCatalyst extends Block {

    public BlockSculkCatalyst() {
        super( "minecraft:sculk_catalyst" );
    }

    @Override
    public ItemSculkCatalyst toItem() {
        return new ItemSculkCatalyst();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SCULK_CATALYST;
    }
}
