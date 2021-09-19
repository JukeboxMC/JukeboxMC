package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemToolType;
import org.jukeboxmc.item.ItemWarpedNylium;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWarpedNylium extends Block {

    public BlockWarpedNylium() {
        super( "minecraft:warped_nylium" );
    }

    @Override
    public ItemWarpedNylium toItem() {
        return new ItemWarpedNylium();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WARPED_NYLIUM;
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
