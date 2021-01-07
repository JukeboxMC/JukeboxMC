package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSand extends Item {

    public ItemSand() {
        super( "minecraft:sand", 12 );
    }

    public void setSandType( SandType sandType ) {
        this.setMeta( sandType.ordinal() );
    }

    public SandType getSandType() {
        return SandType.values()[this.getMeta()];
    }

    public enum SandType {
        SAND,
        RED_SAND
    }
}
