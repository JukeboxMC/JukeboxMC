package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWood extends Item {

    public ItemWood() {
        super( "minecraft:wood", -212 );
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
        DARK_OAK,
        OAK2, // <--- OAK
        OAK3, // <--- OAK
        STRIPPED_OAK,
        STRIPPED_SPRUCE,
        STRIPPED_BIRCH,
        STRIPPED_JUNGLE,
        STRIPPED_ACACIA,
        STRIPPED_DARK_OAK
    }
}
