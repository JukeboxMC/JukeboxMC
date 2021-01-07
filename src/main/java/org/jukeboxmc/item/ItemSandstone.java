package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSandstone extends Item {

    public ItemSandstone() {
        super( "minecraft:sandstone", 24 );
    }

    public void setSandStoneType( SandStoneType sandStoneType ) {
        this.setMeta( sandStoneType.ordinal() );
    }

    public SandStoneType getSandStoneType() {
        return SandStoneType.values()[this.getMeta()];
    }

    public enum SandStoneType {
        SANDSTONE,
        CHISELED_SANDSTONE,
        CUT_SANDSTONE,
        SMOOTH_SANDSTONE
    }
}
