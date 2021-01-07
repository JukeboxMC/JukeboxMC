package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSponge extends Item {

    public ItemSponge() {
        super( "minecraft:sponge", 19 );
    }

    public void setSpongeType( SpongeType spongeType ) {
        this.setMeta( spongeType.ordinal() );
    }

    public SpongeType getSpongeType() {
        return SpongeType.values()[this.getMeta()];
    }

    public enum SpongeType {
        SPONGE,
        WET_SPONGE
    }
}
