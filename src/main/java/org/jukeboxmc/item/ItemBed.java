package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBed;
import org.jukeboxmc.block.BlockColor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBed extends Item {

    public ItemBed() {
        super( "minecraft:bed", 416 );
    }

    @Override
    public BlockBed getBlock() {
        return new BlockBed();
    }

    public void setColor( BlockColor blockColor ) {
        this.setMeta( blockColor.ordinal() );
    }

    public BlockColor getColor() {
        return BlockColor.values()[this.getMeta()];
    }

}
