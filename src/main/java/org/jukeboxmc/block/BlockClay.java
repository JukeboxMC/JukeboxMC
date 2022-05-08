package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemClay;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockClay extends Block {

    public BlockClay() {
        super( "minecraft:clay" );
    }

    @Override
    public ItemClay toItem() {
        return new ItemClay();
    }

    @Override
    public BlockType getType() {
        return BlockType.CLAY;
    }

    @Override
    public double getHardness() {
        return 0.6;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.SHOVEL;
    }

}
