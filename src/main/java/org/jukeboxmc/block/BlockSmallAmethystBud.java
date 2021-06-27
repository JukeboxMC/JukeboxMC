package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemSmallAmethystBud;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSmallAmethystBud extends Block{

    public BlockSmallAmethystBud() {
        super( "minecraft:small_amethyst_bud" );
    }

    @Override
    public ItemSmallAmethystBud toItem() {
        return new ItemSmallAmethystBud();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SMALL_AMETHYST_BUD;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
