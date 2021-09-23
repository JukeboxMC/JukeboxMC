package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemPodzol;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPodzol extends Block {

    public BlockPodzol() {
        super( "minecraft:podzol" );
    }

    @Override
    public ItemPodzol toItem() {
        return new ItemPodzol();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.PODZOL;
    }

    @Override
    public double getHardness() {
        return 0.5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.SHOVEL;
    }

}
