package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWeatheredCutCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWeatheredCutCopper extends Block{

    public BlockWeatheredCutCopper() {
        super( "minecraft:weathered_cut_copper" );
    }

    @Override
    public ItemWeatheredCutCopper toItem() {
        return new ItemWeatheredCutCopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WEATHERED_CUT_COPPER;
    }
}
