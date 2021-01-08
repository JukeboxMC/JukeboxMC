package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStonebrick;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStonebrick extends Item {

    public ItemStonebrick() {
        super( "minecraft:stonebrick", 98 );
    }

    @Override
    public BlockStonebrick getBlock() {
        return new BlockStonebrick();
    }

    public void setStoneType( StoneType stoneType ) {
        this.setMeta( stoneType.ordinal() );
    }

    public StoneType getStoneType() {
        return StoneType.values()[this.getMeta()];
    }

    public enum StoneType {
        STONE_BRICKS,
        MOSSY_STONE_BRICKS,
        CRACKED_STONE_BRICKS,
        CHISELED_STONE_BRICKS
    }

}
