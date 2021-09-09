package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemHardGlassPane;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockHardGlassPane extends BlockWaterlogable {

    public BlockHardGlassPane() {
        super( "minecraft:hard_glass_pane" );
    }

    @Override
    public ItemHardGlassPane toItem() {
        return new ItemHardGlassPane();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.HARD_GLASS_PANE;
    }

}
