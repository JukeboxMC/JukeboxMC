package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSkull;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSkull extends Item {

    public ItemSkull() {
        super ( "minecraft:skull" );
    }

    @Override
    public BlockSkull getBlock() {
        return new BlockSkull();
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
