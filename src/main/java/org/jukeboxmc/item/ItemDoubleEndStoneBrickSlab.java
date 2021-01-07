package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDoubleEndStoneBrickSlab extends Item {

    public ItemDoubleEndStoneBrickSlab() {
        super( "minecraft:double_stone_slab3", -162 );
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
