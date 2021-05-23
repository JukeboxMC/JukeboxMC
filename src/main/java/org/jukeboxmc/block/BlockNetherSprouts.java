package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemNetherSprouts;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockNetherSprouts extends Block {

    public BlockNetherSprouts() {
        super( "minecraft:nether_sprouts" );
    }

    @Override
    public ItemNetherSprouts toItem() {
        return new ItemNetherSprouts();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.NETHER_SPROUTS;
    }

}
