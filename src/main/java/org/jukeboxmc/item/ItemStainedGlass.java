package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockColor;
import org.jukeboxmc.block.BlockStainedGlass;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStainedGlass extends Item {

    public ItemStainedGlass() {
        super( "minecraft:stained_glass", 241 );
    }

    @Override
    public BlockStainedGlass getBlock() {
        return new BlockStainedGlass();
    }

    public void setColor( BlockColor blockColor ) {
        this.setMeta( blockColor.ordinal() );
    }

    public BlockColor getColor() {
        return BlockColor.values()[this.getMeta()];
    }

}
