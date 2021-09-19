package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemRedstoneLamp;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRedstoneLamp extends Block {

    public BlockRedstoneLamp() {
        super( "minecraft:redstone_lamp" );
    }

    @Override
    public ItemRedstoneLamp toItem() {
        return new ItemRedstoneLamp();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.REDSTONE_LAMP;
    }

    @Override
    public double getHardness() {
        return 0.3;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

}
