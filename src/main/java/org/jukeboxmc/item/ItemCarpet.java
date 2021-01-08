package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCarpet;
import org.jukeboxmc.block.BlockColor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCarpet extends Item {

    public ItemCarpet() {
        super( "minecraft:carpet", 171 );
    }

    @Override
    public BlockCarpet getBlock() {
        return new BlockCarpet();
    }

    public void setColor( BlockColor blockColor ) {
        this.setMeta( blockColor.ordinal() );
    }

    public BlockColor getColor() {
        return BlockColor.values()[this.getMeta()];
    }

}
