package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockColor;
import org.jukeboxmc.block.BlockStainedHardenedClay;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStainedHardenedClay extends Item {

    public ItemStainedHardenedClay() {
        super( "minecraft:stained_hardened_clay", 159 );
    }

    @Override
    public BlockStainedHardenedClay getBlock() {
        return new BlockStainedHardenedClay();
    }

    public void setColor( BlockColor blockColor ) {
        this.setMeta( blockColor.ordinal() );
    }

    public BlockColor getColor() {
        return BlockColor.values()[this.getMeta()];
    }

}
