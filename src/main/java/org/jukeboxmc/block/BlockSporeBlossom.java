package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemSporeBlossom;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSporeBlossom extends Block{

    public BlockSporeBlossom() {
        super( "minecraft:spore_blossom" );
    }

    @Override
    public ItemSporeBlossom toItem() {
        return new ItemSporeBlossom();
    }

    @Override
    public BlockType getType() {
        return BlockType.SPORE_BLOSSOM;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
