package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemJungleStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockJungleStairs extends BlockStairs {

    public BlockJungleStairs() {
        super( "minecraft:jungle_stairs" );
    }

    @Override
    public ItemJungleStairs toItem() {
        return new ItemJungleStairs();
    }

    @Override
    public BlockType getType() {
        return BlockType.JUNGLE_STAIRS;
    }

}
