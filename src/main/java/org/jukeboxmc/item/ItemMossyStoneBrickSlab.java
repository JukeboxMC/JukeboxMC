package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDoubleMossyStoneBrickSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemMossyStoneBrickSlab extends Item {

    public ItemMossyStoneBrickSlab() {
        super( "minecraft:double_stone_slab4", -166 );
    }

    @Override
    public BlockDoubleMossyStoneBrickSlab getBlock() {
        return new BlockDoubleMossyStoneBrickSlab();
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
