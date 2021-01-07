package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWoodenSlab extends Item {

    public ItemWoodenSlab() {
        super( "minecraft:wooden_slab", 158 );
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
