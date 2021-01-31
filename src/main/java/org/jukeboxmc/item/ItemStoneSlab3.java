package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStoneSlab3;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStoneSlab3 extends Item {

    public ItemStoneSlab3() {
        super( "minecraft:double_stone_slab3", -162 );
    }

    @Override
    public BlockStoneSlab3 getBlock() {
        return new BlockStoneSlab3();
    }

    public void setSlabType( SlabType slabType ) {
        this.setMeta( slabType.ordinal() );
    }

    public SlabType getSlabType() {
        return SlabType.values()[this.getMeta()];
    }

    public enum SlabType {
        END_STONE_BRICK,
        SMOOTH_RED_SANDSTONE,
        POLISHED_ANDESITE,
        ANDESITE,
        DIORITE,
        POLISHED_DIORITE,
        GRANITE,
        POLISHED_GRANITE
    }
}
