package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPrismarine;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPrismarine extends Item {

    public ItemPrismarine() {
        super( "minecraft:prismarine", 168 );
    }

    @Override
    public BlockPrismarine getBlock() {
        return new BlockPrismarine();
    }

    public void setPrismarineType( PrismarineType prismarineType ) {
        this.setMeta( prismarineType.ordinal() );
    }

    public PrismarineType getPrismarineType() {
        return PrismarineType.values()[this.getMeta()];
    }

    public enum PrismarineType {
        PRISMARINE,
        DARK_PRISMARINE,
        PRISMARINE_BRICKS
    }
}
