package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockColor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStainedHardenedClay extends Item {

    public ItemStainedHardenedClay() {
        super( "minecraft:stained_hardened_clay", 159 );
    }

    public void setColor( BlockColor blockColor ) {
        this.setMeta( blockColor.ordinal() );
    }

    public BlockColor getColor() {
        return BlockColor.values()[this.getMeta()];
    }

}
