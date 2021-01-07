package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDoubleMossyStoneBrickSlab extends Item {

    public ItemDoubleMossyStoneBrickSlab() {
        super( "minecraft:double_stone_slab4", -166 );
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
