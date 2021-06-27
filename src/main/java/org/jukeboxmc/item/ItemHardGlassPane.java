package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockHardGlassPane;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemHardGlassPane extends Item {

    public ItemHardGlassPane() {
        super ( "minecraft:hard_glass_pane" );
    }

    @Override
    public BlockHardGlassPane getBlock() {
        return new BlockHardGlassPane();
    }
}
