package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockFence;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFence extends Item {

    public ItemFence() {
        super( "minecraft:fence", 85 );
    }

    @Override
    public BlockFence getBlock() {
        return new BlockFence();
    }

    public void setWoodType( WoodType woodType ) {
        this.setMeta( woodType.ordinal() );
    }

    public WoodType getWoodType() {
        return WoodType.values()[this.getMeta()];
    }

    public enum WoodType {
        OAK,
        SPRUCE,
        BIRCH,
        JUNGLE,
        ACACIA,
        DARK_OAK
    }

}
