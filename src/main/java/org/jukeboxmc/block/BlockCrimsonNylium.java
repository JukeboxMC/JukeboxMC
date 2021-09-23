package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCrimsonNylium;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCrimsonNylium extends Block {

    public BlockCrimsonNylium() {
        super( "minecraft:crimson_nylium" );
    }

    @Override
    public ItemCrimsonNylium toItem() {
        return new ItemCrimsonNylium();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CRIMSON_NYLIUM;
    }

    @Override
    public double getHardness() {
        return 0.4;
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
