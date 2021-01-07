package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLeaves extends Item {

    public ItemLeaves() {
        super( "minecraft:leaves", 18 );
    }

    public void setLeafType( LeafType leafType ) {
        this.setMeta( leafType.ordinal() );
    }

    public LeafType getLeafType() {
        return LeafType.values()[this.getMeta()];
    }

    public enum LeafType {
        OAK,
        SPRUCE,
        BIRCH,
        JUNGLE
    }
}
