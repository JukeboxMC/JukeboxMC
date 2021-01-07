package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPurpurBlock extends Item {

    public ItemPurpurBlock() {
        super( "minecraft:purpur_block", 201 );
    }

    public void setPurpurType( PurpurType purpurType ) {
        this.setMeta( purpurType.ordinal() );
    }

    public PurpurType getPurpurType() {
        return PurpurType.values()[this.getMeta()];
    }

    public enum PurpurType {
        PURPUR,
        CHISELED_PURPUR,
        PURPUR_PILLAR
    }
}
