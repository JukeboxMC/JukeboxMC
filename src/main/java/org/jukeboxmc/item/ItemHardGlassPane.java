package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockHardGlassPane;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemHardGlassPane extends Item {

    public ItemHardGlassPane() {
        super( 190 );
    }

    @Override
    public BlockHardGlassPane getBlock() {
        return new BlockHardGlassPane();
    }
}
