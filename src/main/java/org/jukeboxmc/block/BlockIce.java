package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemIce;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockIce extends Block {

    public BlockIce() {
        super( "minecraft:ice" );
    }

    @Override
    public ItemIce toItem() {
        return new ItemIce();
    }

    @Override
    public BlockType getType() {
        return BlockType.ICE;
    }

    @Override
    public double getHardness() {
        return 0.5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }
}
