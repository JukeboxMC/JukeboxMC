package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSapling extends Item {

    public ItemSapling() {
        super( "minecraft:sapling", 6 );
    }

    public void setSaplingType( SaplingType saplingType ) {
        this.setMeta( saplingType.ordinal() );
    }

    public SaplingType getSaplingType() {
        return SaplingType.values()[this.getMeta()];
    }

    public enum SaplingType {
        OAK,
        SPRUCE,
        BIRCH,
        JUNGLE,
        ACACIA,
        DARK_OAK
    }

}
