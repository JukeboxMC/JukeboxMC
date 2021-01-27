package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDoubleStoneSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStoneSlab extends Item {

    public ItemStoneSlab() {
        super( "minecraft:double_stone_slab", 44 );
    }

    @Override
    public BlockDoubleStoneSlab getBlock() {
        return new BlockDoubleStoneSlab();
    }

    public void setSlabType( SlabType slabType ) {
        this.setMeta( slabType.ordinal() );
    }

    public SlabType getSlabType() {
        return SlabType.values()[this.getMeta()];
    }

    public enum SlabType {
        SMOOTH_STONE,
        SANDSTONE,
        WOODEN,
        COBBLESTONE,
        BRICKS,
        STONE_BRICKS,
        QUARTZ,
        NETHER_BRICK
    }

}
