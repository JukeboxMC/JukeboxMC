package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemSculkShrieker;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSculkShrieker extends Block {

    public BlockSculkShrieker() {
        super( "minecraft:sculk_shrieker" );
    }

    @Override
    public ItemSculkShrieker toItem() {
        return new ItemSculkShrieker();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SCULK_SHRIEKER;
    }
}
