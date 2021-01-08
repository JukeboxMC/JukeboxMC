package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockColor;
import org.jukeboxmc.block.BlockShulkerBox;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemShulkerBox extends Item {

    public ItemShulkerBox() {
        super( "minecraft:shulker_box", 218 );
    }

    @Override
    public BlockShulkerBox getBlock() {
        return new BlockShulkerBox();
    }

    public void setColor( BlockColor blockColor ) {
        this.setMeta( blockColor.ordinal() );
    }

    public BlockColor getColor() {
        return BlockColor.values()[this.getMeta()];
    }
}
