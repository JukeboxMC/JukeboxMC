package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSkull extends Item {

    public ItemSkull() {
        super( "minecraft:skull", 506 );
    }

    public void setSkullType( SkullType skullType ) {
        this.setMeta( skullType.ordinal() );
    }

    public SkullType getSkullType() {
        return SkullType.values()[this.getMeta()];
    }

    public enum SkullType {
        SKELETON,
        WITHER,
        ZOMBIE,
        STEVE,
        CREEPER,
        DRAGON
    }

}
