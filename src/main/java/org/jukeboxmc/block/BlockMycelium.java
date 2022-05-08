package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemMycelium;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMycelium extends Block {

    public BlockMycelium() {
        super( "minecraft:mycelium" );
    }

    @Override
    public ItemMycelium toItem() {
        return new ItemMycelium();
    }

    @Override
    public BlockType getType() {
        return BlockType.MYCELIUM;
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
