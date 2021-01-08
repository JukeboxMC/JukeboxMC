package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockColor;
import org.jukeboxmc.block.BlockStainedGlassPane;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStainedGlassPane extends Item {

    public ItemStainedGlassPane() {
        super( "minecraft:stained_glass_pane", 160 );
    }

    @Override
    public BlockStainedGlassPane getBlock() {
        return new BlockStainedGlassPane();
    }

    public void setColor( BlockColor blockColor ) {
        this.setMeta( blockColor.ordinal() );
    }

    public BlockColor getColor() {
        return BlockColor.values()[this.getMeta()];
    }

}
