package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPurpurBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPurpurBlock extends Item {

    public ItemPurpurBlock() {
        super( "minecraft:purpur_block", 201 );
    }

    @Override
    public BlockPurpurBlock getBlock() {
        return new BlockPurpurBlock();
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
