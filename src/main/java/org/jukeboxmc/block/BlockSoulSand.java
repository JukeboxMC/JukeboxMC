package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemSoulSand;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSoulSand extends Block {

    public BlockSoulSand() {
        super( "minecraft:soul_sand" );
    }

    @Override
    public ItemSoulSand toItem() {
        return new ItemSoulSand();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SOUL_SAND;
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
