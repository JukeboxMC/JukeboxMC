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
    public BlockType getType() {
        return BlockType.NETHER_SPROUTS;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean canBeReplaced( Block block ) {
        return true;
    }
}
