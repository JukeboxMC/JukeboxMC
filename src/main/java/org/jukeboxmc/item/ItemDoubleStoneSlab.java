package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDoubleStoneSlab extends Item {

    public ItemDoubleStoneSlab() {
        super( "minecraft:double_stone_slab", 44 );
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
