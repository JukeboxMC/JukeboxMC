package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCalcite;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCalcite extends Block{

    public BlockCalcite() {
        super( "minecraft:calcite" );
    }

    @Override
    public ItemCalcite toItem() {
        return new ItemCalcite();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CALCITE;
    }
}
