package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockColor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemShulkerBox extends Item {

    public ItemShulkerBox() {
        super( "minecraft:shulker_box", 218 );
    }

    public void setColor( BlockColor blockColor ) {
        this.setMeta( blockColor.ordinal() );
    }

    public BlockColor getColor() {
        return BlockColor.values()[this.getMeta()];
    }
}
