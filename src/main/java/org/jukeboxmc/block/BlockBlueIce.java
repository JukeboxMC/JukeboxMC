package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBlueIce;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBlueIce extends Block {

    public BlockBlueIce() {
        super( "minecraft:blue_ice" );
    }

    @Override
    public ItemBlueIce toItem() {
        return new ItemBlueIce();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BLUE_ICE;
    }

    @Override
    public double getHardness() {
        return 2.8;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }
}
