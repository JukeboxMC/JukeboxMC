package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemSoulSoil;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSoulSoil extends Block {

    public BlockSoulSoil() {
        super( "minecraft:soul_soil" );
    }

    @Override
    public ItemSoulSoil toItem() {
        return new ItemSoulSoil();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SOUL_SOIL;
    }

    @Override
    public double getHardness() {
        return 1;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.SHOVEL;
    }

}
