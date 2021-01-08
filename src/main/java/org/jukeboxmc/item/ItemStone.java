package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStone extends Item {

    public ItemStone() {
        super( "minecraft:stone", 1 );
    }

    @Override
    public BlockStone getBlock() {
        return new BlockStone();
    }

    public void setStoneType( StoneType stoneType ) {
        this.setMeta( stoneType.ordinal() );
    }

    public StoneType getStoneType() {
        return StoneType.values()[this.getMeta()];
    }

    public enum StoneType {
        STONE,
        GRANITE,
        POLISHED_GRANITE,
        DIORITE,
        POLISHED_DIORITE,
        ANDESITE,
        POLISHED_ANDESITE
    }
}
