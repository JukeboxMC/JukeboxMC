package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStoneSlab;
import org.jukeboxmc.block.type.StoneSlabType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStoneSlab extends Item {

    public ItemStoneSlab() {
        super( 44, 5942 );
    }

    @Override
    public BlockStoneSlab getBlock() {
        return new BlockStoneSlab();
    }

    public void setSlabType( StoneSlabType slabType ) {
        this.setMeta( slabType.ordinal() );
    }

    public StoneSlabType getSlabType() {
        return StoneSlabType.values()[this.getMeta()];
    }

}
