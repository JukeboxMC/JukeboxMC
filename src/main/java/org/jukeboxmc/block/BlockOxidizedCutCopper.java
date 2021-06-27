package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemOxidizedCutCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockOxidizedCutCopper extends Block{

    public BlockOxidizedCutCopper() {
        super( "minecraft:oxidized_cut_copper" );
    }

    @Override
    public ItemOxidizedCutCopper toItem() {
        return new ItemOxidizedCutCopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.OXIDIZED_CUT_COPPER;
    }
}
