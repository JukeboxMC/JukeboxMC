package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStoneSlab4;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStoneSlab4 extends Item {

    public ItemStoneSlab4() {
        super( "minecraft:double_stone_slab4", -166 );
    }

    @Override
    public BlockStoneSlab4 getBlock() {
        return new BlockStoneSlab4();
    }

    public void setSlabType( SlabType slabType ) {
        this.setMeta( slabType.ordinal() );
    }

    public SlabType getSlabType() {
        return SlabType.values()[this.getMeta()];
    }

    public enum SlabType {
        MOSSY_STONE_BRICK,
        SMOOTH_QUARTZ,
        STONE_SLAB,
        CUT_SANDSTONE,
        CUT_RED_SANDSTONE
    }

}
