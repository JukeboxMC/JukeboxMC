package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemMediumAmethystBud;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMediumAmethystBud extends Block {

    public BlockMediumAmethystBud() {
        super("minecraft:medium_amethyst_bud");
    }

    @Override
    public ItemMediumAmethystBud toItem() {
        return new ItemMediumAmethystBud();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.MEDIUM_AMETHYST_BUD;
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
