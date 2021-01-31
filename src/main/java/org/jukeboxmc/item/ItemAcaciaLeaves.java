package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLeaves2;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaLeaves extends Item {

    public ItemAcaciaLeaves() {
        super( "minecraft:leaves2", 161 );
    }

    @Override
    public BlockLeaves2 getBlock() {
        return new BlockLeaves2();
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
