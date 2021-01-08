package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockColor;
import org.jukeboxmc.block.BlockWool;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWool extends Item {

    public ItemWool() {
        super( "minecraft:wool", 35 );
    }

    @Override
    public BlockWool getBlock() {
        return new BlockWool();
    }

    public void setColor( BlockColor blockColor ) {
        this.setMeta( blockColor.ordinal() );
    }

    public BlockColor getColor() {
        return BlockColor.values()[this.getMeta()];
    }
}
