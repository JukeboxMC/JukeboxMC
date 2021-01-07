package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaLeaves extends Item {

    public ItemAcaciaLeaves() {
        super( "minecraft:leaves2", 161 );
    }

    public void setLeafType( LeafType leafType ) {
        this.setMeta( leafType.ordinal() );
    }

    public LeafType getLeafType() {
        return LeafType.values()[this.getMeta()];
    }

    public enum LeafType {
        ACACIA,
        DARK_OAK
    }

}
