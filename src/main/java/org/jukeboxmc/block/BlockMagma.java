package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemMagma;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMagma extends Block {

    public BlockMagma() {
        super( "minecraft:magma" );
    }

    @Override
    public ItemMagma toItem() {
        return new ItemMagma();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.MAGMA;
    }

    @Override
    public double getHardness() {
        return 0.5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

}
